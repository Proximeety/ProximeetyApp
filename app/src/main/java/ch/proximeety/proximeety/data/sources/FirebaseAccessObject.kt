package ch.proximeety.proximeety.data.sources

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import ch.proximeety.proximeety.BuildConfig
import ch.proximeety.proximeety.R
import ch.proximeety.proximeety.core.entities.*
import ch.proximeety.proximeety.util.SyncActivity
import ch.proximeety.proximeety.util.extensions.await
import ch.proximeety.proximeety.util.extensions.observeOnce
import ch.proximeety.proximeety.util.extensions.rotate
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*


/**
 * Object used to access firebase.
 * @param context the application context.
 */
class FirebaseAccessObject(
    private val context: Context
) {

    companion object {
        /**
         * Intent request code for Google Authentication
         */
        private const val REQUEST_CODE_GOOGLE_SIGN_IN = 1

        private const val TAG = "FIREBASE_ACCESS_OBJECT"

        private const val USER_PATH = "users"
        private const val USER_DISPLAY_NAME_KEY = "displayName"
        private const val USER_NAME_KEY = "displayName"
        private const val USER_GIVEN_NAME_KEY = "givenName"
        private const val USER_FAMILY_NAME_KEY = "familyName"
        private const val USER_EMAIL_KEY = "email"
        private const val USER_PROFILE_PICTURE_KEY = "profilePicture"
        private const val USER_HAS_STORY_KEY = "hasStory"
        private const val USER_ID_KEY = "id"

        private const val USER_FRIENDS_PATH = "usersFriends"

        private const val POSTS_PATH = "posts"
        private const val POST_POSTER_ID_KEY = "posterId"
        private const val POST_USER_DISPLAY_NAME_KEY = "userDisplayName"
        private const val POST_USER_PROFILE_PICTURE_KEY = "userProfilePicture"
        private const val POST_TIMESTAMP_KEY = "timestamp"
        private const val POST_URL_KEY = "postURL"
        private const val POST_LIKES_KEY = "likes"

        private const val COMMENT_PATH = "comments"
        private const val COMMENT_POST_ID_KEY = "postId"
        private const val COMMENT_POSTER_ID_KEY = "posterId"
        private const val COMMENT_USER_DISPLAY_NAME_KEY = "userDisplayName"
        private const val COMMENT_USER_PROFILE_PICTURE_KEY = "userProfilePicture"
        private const val COMMENT_TIMESTAMP_KEY = "timestamp"
        private const val COMMENT_VALUE_KEY = "comment"
        private const val COMMENT_LIKES_KEY = "likes"
        private const val COMMENT_REPLIES_KEY = "replies"

        private const val REPLY_PATH = "replies"
        private const val REPLY_COMMENT_ID_KEY = "commentId"
        private const val REPLY_POSTER_KEY = "posterId"
        private const val REPLY_USER_DISPLAY_NAME_KEY = "userDisplayName"
        private const val REPLY_USER_PROFILE_PICTURE_KEY = "userProfilePicture"
        private const val REPLY_TIMESTAMP_KEY = "userProfilePicture"
        private const val REPLY_VALUE_KEY = "commentReply"

        private const val STORY_PATH = "stories"
        private const val STORY_POSTER_ID_KEY = "posterId"
        private const val STORY_USER_DISPLAY_NAME_KEY = "userDisplayName"
        private const val STORY_USER_PROFILE_PICTURE_KEY = "userProfilePicture"
        private const val STORY_TIMESTAMP_KEY = "timestamp"
        private const val STORY_URL_KEY = "storyURL"

        private const val USER_LOCATION_PATH = "usersLocations"
        private const val USER_LOCATION_LATITUDE_KEY = "latitude"
        private const val USER_LOCATION_LONGITUDE_KEY = "longitude"
        private const val USER_LOCATION_TIMESTAMP_KEY = "timestamp"

        private const val STORAGE_POST_PATH = "posts"
        private const val STORAGE_STORY_PATH = "stories"

        private const val TAG_PATH = "tags"
        private const val TAG_ID_KEY = "id"
        private const val TAG_NAME_KEY = "name"
        private const val TAG_LATITUDE_KEY = "latitude"
        private const val TAG_LONGITUDE_KEY = "longitude"
        private const val TAG_VISITORS_KEY = "visitors"
        private const val TAG_VISITORS_TIMESTAMP_KEY = "timestamp"
        private const val TAG_OWNER_KEY = "owner"


    }

    private var auth: FirebaseAuth
    private var database: DatabaseReference
    private var storage: StorageReference

    private var authenticatedUser: MutableLiveData<User?>? = null

    init {
        Firebase.database.setPersistenceEnabled(false)
        if (BuildConfig.DEBUG) {
            Firebase.auth.useEmulator("10.0.2.2", 9099)
            Firebase.database.useEmulator("10.0.2.2", 9000)
            Firebase.storage.useEmulator("10.0.2.2", 9199)
        }
        auth = Firebase.auth
        database = Firebase.database.reference
        storage = Firebase.storage.reference
    }

    /**
     * Gets the currently authenticated user.
     * @return The authenticated user.
     */
    fun getAuthenticatedUser(lifecycleOwner: LifecycleOwner?): LiveData<User?> {
        if (auth.currentUser != null) {
            if (authenticatedUser != null) return authenticatedUser!!
            authenticatedUser =
                MutableLiveData(User(auth.currentUser!!.uid, auth.currentUser!!.uid))

            val fetchedUser = fetchUserById(auth.currentUser!!.uid, lifecycleOwner)

            if (lifecycleOwner != null) {
                fetchedUser.observe(lifecycleOwner) {
                    if (it != null) {
                        authenticatedUser?.value = it
                    }
                }
            } else {
                fetchedUser.observeOnce {
                    if (it != null) {
                        authenticatedUser?.value = it
                    }
                }
            }

            return authenticatedUser!!
        }
        return MutableLiveData(null)
    }

    /**
     * Sign out the currently authenticated user.
     */
    fun signOut() {
        authenticatedUser = null
        auth.signOut()
    }

    /**
     * Authenticate a new user with Google.
     * @param activity the current activity, needed to launch the Google authentication process.
     * @return The authenticated user.
     */
    suspend fun authenticateWithGoogle(activity: SyncActivity): User? {
        try {

            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.resources.getString(R.string.firebase_server_client_id))
                .requestEmail()
                .requestProfile()
                .requestId()
                .build()

            val googleSignInClient = GoogleSignIn.getClient(activity, gso)
            val intent = googleSignInClient.signInIntent

            googleSignInClient.signOut()

            val result = activity.waitForIntentResult({
                activity.startActivityForResult(intent, REQUEST_CODE_GOOGLE_SIGN_IN)
            }, REQUEST_CODE_GOOGLE_SIGN_IN)

            result?.also { activityResult ->
                if (activityResult.resultCode == Activity.RESULT_OK) {
                    try {
                        val account =
                            GoogleSignIn.getSignedInAccountFromIntent(activityResult.data).result
                        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                        auth.signInWithCredential(credential).await().user?.also { firebaseUser ->
                            val user = User(
                                id = firebaseUser.uid,
                                displayName = account.displayName ?: firebaseUser.uid,
                                givenName = account.givenName,
                                familyName = account.familyName,
                                email = account.email,
                                profilePicture = account.photoUrl?.toString()
                            )
                            uploadUser(user)
                            return user
                        }
                    } catch (e: ApiException) {
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Authenticate with Google failed", e)
           return null
        }

        return null
    }

    private fun uploadUser(user : User) {
        database.child(USER_PATH).child(user.id).setValue(
            mapOf(
                USER_DISPLAY_NAME_KEY to user.displayName,
                USER_NAME_KEY to user.displayName,
                USER_GIVEN_NAME_KEY to user.givenName,
                USER_FAMILY_NAME_KEY to user.familyName,
                USER_EMAIL_KEY to user.email,
                USER_PROFILE_PICTURE_KEY to user.profilePicture
            )
        ).addOnSuccessListener {
            Log.d(
                TAG,
                "Added user"
            )
        }.addOnFailureListener {
            Log.e(
                TAG,
                it.message.toString()
            )
        }
    }

    /**
     * Authenticate with email and password.
     *
     * @param email the email of the user.
     * @param password the password of the user.
     *
     * @return The authenticated user.
     */
    suspend fun authenticateWithEmailAndPassword(email: String, password: String): User? {
        val firebaseUser = try {
            auth.createUserWithEmailAndPassword(email, password).await().user
        } catch (e: FirebaseAuthUserCollisionException) {
            try {
                auth.signInWithEmailAndPassword(email, password).await().user
            } catch (e: java.lang.Exception) {
                null
            }
        } catch (e: Exception) {
            null
        }

        if (firebaseUser != null) {
            val user = User(
                id = firebaseUser.uid,
                displayName = firebaseUser.displayName ?: firebaseUser.uid,
                email = firebaseUser.email,
                profilePicture = firebaseUser.photoUrl?.toString()
            )

            uploadUser(user)

            authenticatedUser = MutableLiveData(user)

            return user
        }

        return null
    }

    /**
     * Fetch a user using it's id.
     *
     * @param id the id of the user to fetch.
     * @param owner the [LifecycleOwner]. As long as the owner is alive, the user will be updated. If null is passed, the user will be updated once.
     * @return A [LiveData] of the user.
     */
    fun fetchUserById(id: String, owner: LifecycleOwner?): LiveData<User?> {
        val user = MutableLiveData<User>(null)
        val ref = database.child(USER_PATH).child(id)
        val listener = ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    user.value =
                            User(
                                id = id,
                                displayName = snapshot.child(USER_DISPLAY_NAME_KEY).value as String?
                                ?: id,
                            givenName = snapshot.child(USER_GIVEN_NAME_KEY).value as String?,
                            familyName = snapshot.child(USER_FAMILY_NAME_KEY).value as String?,
                            email = snapshot.child(USER_EMAIL_KEY).value as String?,
                            profilePicture = snapshot.child(USER_PROFILE_PICTURE_KEY).value as String?,
                            hasStories = snapshot.child(USER_HAS_STORY_KEY).value as Boolean? == true,
                        )
                }
                if (owner == null) {
                    ref.removeEventListener(this)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, error.message)
                if (owner == null) {
                    ref.removeEventListener(this)
                }
            }
        })

        owner?.lifecycle?.addObserver(LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_DESTROY -> ref.removeEventListener(listener)
                else -> {}
            }
        })

        return user
    }

    /**
     * Adds a friend to the authenticated user.
     * @param id the id of the friend.
     */
    suspend fun addFriend(id: String) {
        authenticatedUser?.value?.also {
            database.child(USER_FRIENDS_PATH).child(it.id).child(id).setValue(true).await()
        }
    }

    fun writeNewUser(userId: String, name: String, email: String) {
        val user = User(name, email)

        database.child("users").child(userId).setValue(user)
    }

    fun readAllMessages(userId: String, name: String, email: String) {
        database.child("messages").child(userId).get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
        }.addOnFailureListener {
            Log.i("firebase", "Error getting data", it)
        }
    }


    /**
     * Removes a friend to the authenticated user.
     * @param id the id of the friend.
     */
    suspend fun removeFriend(id: String) {
        authenticatedUser?.value?.also {
            database.child(USER_FRIENDS_PATH).child(it.id).child(id).setValue(false).await()
        }
    }

    /**
     * Gets the friends of a users.
     */
    suspend fun getFriends(): List<User> {
        authenticatedUser?.value?.also { user ->
            val friendsIds = database.child(USER_FRIENDS_PATH).child(user.id).get()
                .await().children.filter { it.value as? Boolean == true }.mapNotNull { it.key }

            return friendsIds.mapNotNull { id -> fetchUserById(id, null).await() }
        }
        return listOf()
    }

    /**
     * Gets all the posts of a users. This function doesn't the download the post images. You have to call [downloadPost] afterwards for each of them.
     * @param userId the id of the user whose posts are downloaded.
     */
    suspend fun getPostsByUserID(userId: String): List<Post> {
        return database.child(POSTS_PATH).child(userId).get()
            .await().children.mapNotNull { snapshotToPost(it) }
    }

    /**
     * Convert a snapshot of a post to a [Post].
     *
     * @param snapshot the snapshot coming from the database.
     * @return A [Post] if the snapshot could be converted, null otherwise.
     */
    private fun snapshotToPost(snapshot: DataSnapshot): Post? {
        if (snapshot.exists() && snapshot.key != null) {
            val posterId =
                snapshot.child(POST_POSTER_ID_KEY).value as String?
            val userDisplayName =
                snapshot.child(POST_USER_DISPLAY_NAME_KEY).value as String?
            val userProfilePicture =
                snapshot.child(POST_USER_PROFILE_PICTURE_KEY).value as String?
            val timestamp = snapshot.child(POST_TIMESTAMP_KEY).value as Long?
            val likes =
                snapshot.child(POST_LIKES_KEY).children.mapNotNull { it.value as? Boolean }
                    .count { it }
            if (posterId != null && userDisplayName != null && timestamp != null) {
                return Post(
                    snapshot.key!!,
                    posterId,
                    userDisplayName,
                    userProfilePicture,
                    timestamp,
                    null,
                    likes
                )
            }
        }
        return null
    }

    /**
     * Gets all the stories of a users. This function does the download the stories images. You have to call [downloadStory] afterwards for each of them.
     * @param userId the id of the user whose posts are downloaded.
     */
    suspend fun getStoriesByUserID(userId: String): List<Story> {
        return database.child(STORY_PATH).child(userId).get()
            .await().children.mapNotNull { snapshot ->
                if (snapshot.exists() && snapshot.key != null) {
                    val posterId =
                        snapshot.child(POST_POSTER_ID_KEY).value as String?
                    val userDisplayName =
                        snapshot.child(POST_USER_DISPLAY_NAME_KEY).value as String?
                    val userProfilePicture =
                        snapshot.child(POST_USER_PROFILE_PICTURE_KEY).value as String?
                    val timestamp = snapshot.child(POST_TIMESTAMP_KEY).value as Long?
                    if (posterId != null && userDisplayName != null && timestamp != null) {
                        return@mapNotNull Story(
                            snapshot.key!!,
                            posterId,
                            userDisplayName,
                            userProfilePicture,
                            timestamp,
                            null,
                        )
                    }
                }
                return@mapNotNull null
            }
    }

    /**
     * Gets the file at the URL and load it into a [ByteArray].
     */
    private fun urlToData(url: String): ByteArray? {
        val uri = Uri.parse(url)
        val orientation =
            context.contentResolver.openFileDescriptor(uri, "r")?.fileDescriptor?.let {
                ExifInterface(
                    it
                ).getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED
                )
            } ?: ExifInterface.ORIENTATION_UNDEFINED
        val bitmap = BitmapFactory.decodeStream(
            BufferedInputStream(
                context.contentResolver.openInputStream(uri)
            )
        ).rotate(orientation) ?: return null
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos)
        return baos.toByteArray()
    }

    /**
     * Posts a new picture.
     *
     * @param url the url of the picture (local file).
     */
    suspend fun post(url: String) {
        authenticatedUser?.value?.let { user ->
            val ref = database.child(POSTS_PATH).child(user.id).push()
            ref.key?.also { key ->
                val data = urlToData(url) ?: return
                try {
                    storage.child(STORAGE_POST_PATH).child(key).putBytes(data).await()
                    ref.setValue(
                        mapOf(
                            POST_POSTER_ID_KEY to user.id,
                            POST_USER_DISPLAY_NAME_KEY to user.displayName,
                            POST_USER_PROFILE_PICTURE_KEY to user.profilePicture,
                            POST_TIMESTAMP_KEY to Calendar.getInstance().timeInMillis
                        )
                    ).await()
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to post picture", e)
                }
            }
        }
    }

    /**
     * Delete a post.
     *
     * @param postId the id of the picture (local file).
     */
    suspend fun deletePost(postId: String) {
        authenticatedUser?.value?.also { user ->
            database.child(POSTS_PATH).child(user.id).child(postId).removeValue().await()
        }
    }

    /**
     * Delete a story.
     *
     * @param storyId the id of the story (local file).
     */
    suspend fun deleteStory(storyId: String) {
        authenticatedUser?.value?.also { user ->
            database.child(STORY_PATH).child(user.id).child(storyId).removeValue().await()
        }
    }

    /**
     * Downloads the content of a post. This must be used [Post.postURL] is null.
     * The function [getPostsByUserID] only returns the metadata of posts but does not download each images.
     * @param post the post to download.
     * @return The same post with a non-null [Post.postURL].
     */
    suspend fun downloadPost(post: Post): Post {
        return try {
            val localFile = File.createTempFile("images", "jpeg")
            storage.child(POSTS_PATH).child(post.id).getFile(localFile).await()
            post.copy(postURL = localFile.toURI().toString())
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            post
        }
    }

    suspend fun isPostLiked(post: Post): Boolean {
        authenticatedUser?.value?.also { user ->
            val ref =
                database.child(POSTS_PATH).child(post.posterId).child(post.id).child(POST_LIKES_KEY)
                    .child(user.id)
            (ref.get().await().value as? Boolean)?.also {
                return it
            }
        }
        return false
    }

    /**
     * Like or unlike the post.
     */
    suspend fun togglePostLike(post: Post) {
        authenticatedUser?.value?.also { user ->
            val ref =
                database.child(POSTS_PATH).child(post.posterId).child(post.id).child(POST_LIKES_KEY)
                    .child(user.id)
            (ref.get().await().value as? Boolean).also {
                ref.setValue(if (it == null) true else !it)
            }
        }
    }

    /**
     * Post a story.
     */
    suspend fun postStory(url: String) {
        authenticatedUser?.value?.let { user ->
            val ref = database.child(STORY_PATH).child(user.id).push()
            ref.key?.also { key ->
                val data = urlToData(url) ?: return
                try {
                    storage.child(STORAGE_STORY_PATH).child(key).putBytes(data).await()
                    ref.setValue(
                        mapOf(
                            STORY_POSTER_ID_KEY to user.id,
                            STORY_USER_DISPLAY_NAME_KEY to user.displayName,
                            STORY_USER_PROFILE_PICTURE_KEY to user.profilePicture,
                            STORY_TIMESTAMP_KEY to Calendar.getInstance().timeInMillis
                        )
                    ).await()
                    database.child(USER_PATH).child(user.id).child(USER_HAS_STORY_KEY)
                        .setValue(true).await()
                } catch (e: Exception) {
                    Log.e(TAG, e.message.toString())
                }
            }
        }
    }

    /**
     * Downloads the content of a story.
     *
     * @param story the post to download.
     * @return The same story with a non-null [Story.storyURL].
     */
    suspend fun downloadStory(story: Story): Story {
        return try {
            val localFile = File.createTempFile("images", "jpeg")
            storage.child(STORY_PATH).child(story.id).getFile(localFile).await()
            story.copy(storyURL = localFile.toURI().toString())
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            story
        }
    }

    /**
     * Upload the location of the user.
     */
    fun uploadLocation(latitude: Double, longitude: Double) {
        authenticatedUser?.value?.let { user ->
            database.child(USER_LOCATION_PATH).child(user.id).setValue(
                mapOf(
                    USER_LOCATION_TIMESTAMP_KEY to Calendar.getInstance().timeInMillis,
                    USER_LOCATION_LATITUDE_KEY to latitude,
                    USER_LOCATION_LONGITUDE_KEY to longitude
                )
            )
        }
    }

    /**
     * Gets the locations of the friends.
     */
    fun getFriendsLocation(owner: LifecycleOwner?): LiveData<Map<String, Triple<Long, Double, Double>>> {
        val result = MutableLiveData<Map<String, Triple<Long, Double, Double>>>(mapOf())
        val ref = database.child(USER_LOCATION_PATH)
        val listener = ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { child ->
                    child.key?.also {
                        val newLocation = it to Triple(
                            child.child(USER_LOCATION_TIMESTAMP_KEY).value as Long,
                            child.child(USER_LOCATION_LATITUDE_KEY).value as Double,
                            child.child(USER_LOCATION_LONGITUDE_KEY).value as Double,
                        )
                        result.value = result.value?.plus(newLocation) ?: mapOf(newLocation)
                    }
                }

                if (owner == null) {
                    ref.removeEventListener(this)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                if (owner == null) {
                    ref.removeEventListener(this)
                }
            }
        })

        owner?.lifecycle?.addObserver(LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_DESTROY -> ref.removeEventListener(listener)
                else -> {}
            }
        })

        return result
    }

    /**
     * Leaves a comment under a post.
     *
     * @param postId the post's id
     * @param comment the comment to leave
     */
    suspend fun postComment(postId: String, comment: String) {
        authenticatedUser?.value?.let { user ->
            val ref = database.child(COMMENT_PATH).child(postId).push()
            ref.key?.also {
                try {
                    ref.setValue(
                        mapOf(
                            COMMENT_POSTER_ID_KEY to user.id,
                            COMMENT_POST_ID_KEY to postId,
                            COMMENT_VALUE_KEY to comment,
                            COMMENT_USER_DISPLAY_NAME_KEY to user.displayName,
                            COMMENT_USER_PROFILE_PICTURE_KEY to user.profilePicture,
                            COMMENT_TIMESTAMP_KEY to Calendar.getInstance().timeInMillis
                        )
                    ).await()
                } catch (e: Exception) {
                    Log.e(TAG, e.message.toString())
                }
            }
        }
    }

    /**
     * Reply to a user's comment
     */
    suspend fun replyToComment(postId: String, commentId: String, comment: String) {
        authenticatedUser?.value?.let { user ->
            var ref = database.child(REPLY_PATH).child(commentId).push()
            ref.key?.also {
                try {
                    ref.setValue(
                        mapOf(
                            REPLY_POSTER_KEY to user.id,
                            REPLY_COMMENT_ID_KEY to commentId,
                            REPLY_VALUE_KEY to comment,
                            REPLY_USER_DISPLAY_NAME_KEY to user.displayName,
                            REPLY_TIMESTAMP_KEY to Calendar.getInstance().timeInMillis
                        )
                    )
                } catch (e: Exception) {
                    Log.e(TAG, e.message.toString())
                }
            }

            ref = database.child(COMMENT_PATH).child(postId).child(commentId).child(
                COMMENT_REPLIES_KEY
            )
            (ref.get().await().value as? Int).also {
                if (it != null) {
                    ref.setValue(it + 1 )
                }
            }

        }
    }

    suspend fun getComments(id: String): List<Comment> {
        return database.child(COMMENT_PATH).child(id).get()
            .await().children.mapNotNull { snapshot ->
                if (snapshot.exists() && snapshot.key != null) {

                    val postId = snapshot.child(COMMENT_POST_ID_KEY).value as String?
                    val posterId = snapshot.child(COMMENT_POSTER_ID_KEY).value as String?
                    val userDisplayName =
                        snapshot.child(COMMENT_USER_DISPLAY_NAME_KEY).value as String?
                    val userProfilePicture =
                        snapshot.child(COMMENT_USER_PROFILE_PICTURE_KEY).value as String?
                    val timestamp = snapshot.child(COMMENT_TIMESTAMP_KEY).value as Long?
                    val comment = snapshot.child(
                        COMMENT_VALUE_KEY
                    ).value as String?

                    val replies =
                        snapshot.child(COMMENT_LIKES_KEY).value as Int

                    val likes =
                        snapshot.child(COMMENT_LIKES_KEY).children.mapNotNull { it.value as? Boolean }
                            .filter { it }.count()

                    if (postId != null && posterId != null && userDisplayName != null && timestamp != null && comment != null) {

                        return@mapNotNull Comment(
                            id = snapshot.key!!,
                            postId = postId,
                            posterId = posterId,
                            userDisplayName = userDisplayName,
                            userProfilePicture = userProfilePicture,
                            timestamp = timestamp,
                            comment = comment,
                            likes = likes,
                            isLiked = false
                        )
                    }
                }
                return@mapNotNull null
            }
    }

    suspend fun isCommentLiked(comment: Comment): Boolean {
        authenticatedUser?.value?.also { user ->
            val ref =
                database
                    .child(COMMENT_PATH)
                    .child(comment.postId)
                    .child(comment.id)
                    .child(COMMENT_LIKES_KEY)
                    .child(user.id)
            (ref.get().await().value as? Boolean)?.also {
                return it
            }
        }
        return false
    }

    /**
     * Like or unlike the comment.
     */
    suspend fun toggleCommentLike(comment: Comment) {
        authenticatedUser?.value?.also { user ->
            val ref =
                database.child(COMMENT_PATH)
                    .child(comment.postId)
                    .child(comment.id)
                    .child(COMMENT_LIKES_KEY)
                    .child(user.id)
            (ref.get().await().value as? Boolean).also {
                ref.setValue(if (it == null) true else !it)
            }
        }
    }

    /**
     * Gets the tag by id.
     */
    suspend fun getTagById(id: String): Tag? {
        val snapshot = database.child(TAG_PATH).child(id).get().await()

        if (snapshot.exists() && snapshot.key != null) {
            val name = snapshot.child(TAG_NAME_KEY).value as String?
            val longitude = snapshot.child(TAG_LONGITUDE_KEY).value as Double?
            val latitude = snapshot.child(TAG_LATITUDE_KEY).value as Double?
            val visitors = snapshot.child(TAG_VISITORS_KEY).children.mapNotNull {
                val name = it.child(USER_DISPLAY_NAME_KEY).value as String?
                val profilePicture = it.child(USER_PROFILE_PICTURE_KEY).value as String?
                val timestamp = it.child(TAG_VISITORS_TIMESTAMP_KEY).value as Long?
                if (name != null && timestamp != null) {
                    return@mapNotNull Pair(
                        timestamp, User(
                            id = it.key!!,
                            displayName = name,
                            profilePicture = profilePicture
                        )
                    )
                }
                return@mapNotNull null
            }

            val owner = snapshot.child(TAG_OWNER_KEY).let {
                if (it.exists() && it.key != null) {
                    val id = it.child(USER_ID_KEY).value as String?
                    val name = it.child(USER_DISPLAY_NAME_KEY).value as String?
                    val profilePicture = it.child(USER_PROFILE_PICTURE_KEY).value as String?
                    if (id != null && name != null) {
                        return@let User(
                            id = id,
                            displayName = name,
                            profilePicture = profilePicture
                        )
                    }
                }
                return@let null
            }

            if (name != null && longitude != null && latitude != null && owner != null) {
                return Tag(id, name, latitude, longitude, visitors, owner)
            }
        }
        return null
    }

    /**
     * Set the authenticated user as a visitor of the tag.
     */
    suspend fun visitTag(tagId: String) {
        authenticatedUser?.value?.also { user ->
            database.child(TAG_PATH).child(tagId).child(TAG_VISITORS_KEY).child(user.id).setValue(
                mapOf(
                    TAG_VISITORS_TIMESTAMP_KEY to Calendar.getInstance().timeInMillis,
                    USER_DISPLAY_NAME_KEY to user.displayName,
                    USER_PROFILE_PICTURE_KEY to user.profilePicture
                )
            ).await()
        }
    }

    /**
     *  Retrieve all existing NFC tags
     */
    suspend fun getAllNfcs(): List<Tag> {
        authenticatedUser?.value?.also {
            val tags = database.child(TAG_PATH).get().await().children.mapNotNull { it.key }
            return tags.mapNotNull { id -> getTagById(id) }
        }
        return listOf()
    }
     
    /**
     * Get a certain post from a user.
     * @param userId the id of the poster.
     * @param postId the id of the post
     */
    suspend fun getPostByIds(userId: String, postId: String): Post? {
        val snapshot = database.child(POSTS_PATH).child(userId).child(postId).get().await()
        return snapshotToPost(snapshot)
    }

    suspend fun writeTag(tag: Tag) {
        database.child(TAG_PATH).child(tag.id).setValue(
            mapOf(
                TAG_NAME_KEY to tag.name,
                TAG_LONGITUDE_KEY to tag.longitude,
                TAG_LATITUDE_KEY to tag.latitude,
                TAG_OWNER_KEY to mapOf(
                    USER_ID_KEY to tag.owner.id,
                    USER_DISPLAY_NAME_KEY to tag.owner.displayName,
                    USER_PROFILE_PICTURE_KEY to tag.owner.profilePicture
                )
            )
        )
    }
}

