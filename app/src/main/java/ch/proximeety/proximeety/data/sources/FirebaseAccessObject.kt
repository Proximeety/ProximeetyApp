package ch.proximeety.proximeety.data.sources

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import ch.proximeety.proximeety.R
import ch.proximeety.proximeety.core.entities.Post
import ch.proximeety.proximeety.core.entities.Story
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.util.SyncActivity
import ch.proximeety.proximeety.util.extensions.await
import ch.proximeety.proximeety.util.extensions.rotate
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
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

        private const val USER_FRIENDS_PATH = "usersFriends"

        private const val POSTS_PATH = "posts"
        private const val POST_POSTER_ID_KEY = "posterId"
        private const val POST_USER_DISPLAY_NAME_KEY = "userDisplayName"
        private const val POST_USER_PROFILE_PICTURE_KEY = "userProfilePicture"
        private const val POST_TIMESTAMP_KEY = "timestamp"
        private const val POST_URL_KEY = "postURL"
        private const val POST_LIKES_KEY = "likes"

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
    }

    private var auth: FirebaseAuth
    private var database: DatabaseReference
    private var storage: StorageReference

    private var authenticatedUser: MutableLiveData<User?>? = null

    init {
        Firebase.database.setPersistenceEnabled(false)
        auth = Firebase.auth
        database = Firebase.database.reference
        storage = Firebase.storage.reference
    }

    /**
     * Gets the currently authenticated user.
     * @return The authenticated user.
     */
    fun getAuthenticatedUser(lifecycleOwner: LifecycleOwner): LiveData<User?> {
        if (auth.currentUser != null) {
            if (authenticatedUser != null) return authenticatedUser!!
            authenticatedUser =
                MutableLiveData(User(auth.currentUser!!.uid, auth.currentUser!!.uid))
            fetchUserById(auth.currentUser!!.uid, lifecycleOwner).observe(lifecycleOwner) {
                if (it != null) {
                    authenticatedUser!!.value = it
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
        auth.signOut()
    }

    /**
     * Authenticate a new user with Google.
     * @param activity the current activity, needed to launch the Google authentication process.
     * @return The authenticated user.
     */
    suspend fun authenticateWithGoogle(activity: SyncActivity): User? {

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
                        return user
                    }
                } catch (e: ApiException) {
                }
            }
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
    fun addFriend(id: String) {
        authenticatedUser?.value?.also {
            database.child(USER_FRIENDS_PATH).child(it.id).child(id).setValue(true)
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
     * Gets the friends of a users.
     */
    suspend fun getFriends(): List<User> {
        authenticatedUser?.value?.also { user ->
            val friendsIds = database.child(USER_FRIENDS_PATH).child(user.id).get()
                .await().children.mapNotNull { it.key }

            return friendsIds.mapNotNull { id -> fetchUserById(id, null).await() }
        }
        return listOf()
    }

    /**
     * Gets all the posts of a users. This function does the download the post images. You have to call [downloadPost] afterwards for each of them.
     * @param userId the id of the user whose posts are downloaded.
     */
    suspend fun getPostsByUserID(userId: String): List<Post> {
        return database.child(POSTS_PATH).child(userId).get()
            .await().children.mapNotNull { snapshot ->
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
                            .filter { it }.count()
                    if (posterId != null && userDisplayName != null && userProfilePicture != null && timestamp != null) {
                        return@mapNotNull Post(
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
                return@mapNotNull null
            }
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
                    if (posterId != null && userDisplayName != null && userProfilePicture != null && timestamp != null) {
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
                    )
                } catch (e: Exception) {
                    Log.e(TAG, e.message.toString())
                }
            }
        }
    }

    /**
     * Delete a posts.
     *
     * @param postID the id of the picture (local file).
     */
    fun deletePost(postId: String) {
        Log.d("UserRepositoryImplementaiton", "llega a deletePost")
        authenticatedUser?.value?.also { user ->
            database.child(POSTS_PATH).child(user.id).child(postId).removeValue()
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
                    )
                    database.child(USER_PATH).child(user.id).child(USER_HAS_STORY_KEY)
                        .setValue(true)
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
                val map = mutableMapOf<String, Pair<Int, Int>>()
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
}
