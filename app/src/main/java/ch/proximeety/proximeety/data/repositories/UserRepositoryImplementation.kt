package ch.proximeety.proximeety.data.repositories

import android.content.Context
import android.location.Location
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import ch.proximeety.proximeety.core.entities.*
import ch.proximeety.proximeety.core.repositories.UserRepository
import ch.proximeety.proximeety.data.sources.BluetoothService
import ch.proximeety.proximeety.data.sources.FirebaseAccessObject
import ch.proximeety.proximeety.data.sources.LocationService
import ch.proximeety.proximeety.data.sources.NfcService
import ch.proximeety.proximeety.data.sources.cache.AuthenticatedUserCache
import ch.proximeety.proximeety.data.sources.cache.FriendCacheDao
import ch.proximeety.proximeety.data.sources.cache.PostCacheDao
import ch.proximeety.proximeety.util.SyncActivity
import ch.proximeety.proximeety.util.extensions.await
import ch.proximeety.proximeety.util.extensions.isConnected
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

/**
 * Implementation of the user repository user Firebase.
 * @param firebaseAccessObject the object used to access firebase.
 */
class UserRepositoryImplementation(
    context: Context,
    private val firebaseAccessObject: FirebaseAccessObject,
    private val bluetoothService: BluetoothService,
    private val locationService: LocationService,
    private val postCacheDao: PostCacheDao,
    private val friendsCacheDao: FriendCacheDao,
    private val authenticatedUserCache: AuthenticatedUserCache,
    private val nfcService: NfcService
) : UserRepository {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private lateinit var activity: SyncActivity

    override fun setActivity(activity: SyncActivity) {
        this.activity = activity
    }

    override fun getAuthenticatedUser(): LiveData<User?> {
        val user = MutableLiveData<User?>(null)

        val firebaseUser = firebaseAccessObject.getAuthenticatedUser(activity)
        firebaseUser.observe(activity) {
            if (connectivityManager.isConnected()) {
                if (it != null) {
                    authenticatedUserCache.user = it
                    user.value = it
                }
            }
        }

        if (connectivityManager.isConnected()) {
            user.value = firebaseUser.value
        } else {
            val cachedUser = authenticatedUserCache.user
            if (cachedUser != null && firebaseUser.value?.id == cachedUser.id) {
                user.value = cachedUser
            } else {
                user.value = firebaseUser.value
            }
        }

        return user
    }


    override suspend fun authenticateWithGoogle(): User? {
        return firebaseAccessObject.authenticateWithGoogle(activity)
    }

    override suspend fun setAuthenticatedUserVisible() {
        getAuthenticatedUser().await()?.also {
            bluetoothService.advertiseUser(it, activity)
        }
    }

    override fun getNearbyUsers(): LiveData<List<User>> {
        val users = MutableLiveData(listOf<User>())
        val requestedIds = listOf<String>()
        activity.lifecycleScope.launch(Dispatchers.Main) {
            bluetoothService.scanForUsers(activity).observe(activity) { list ->
                activity.lifecycleScope.launch(Dispatchers.Main) {
                    list.filterNot { requestedIds.contains(it.id) }.forEach { user ->
                        firebaseAccessObject.fetchUserById(user.id, activity)
                            .observe(activity) { fetchUser ->
                                if (fetchUser != null) {
                                    users.postValue(users.value?.filter { it.id == fetchUser.id }
                                        ?.plus(fetchUser)?.distinctBy { it.id })
                                }
                            }
                    }
                }
            }
        }
        return users
    }

    override fun signOut() {
        firebaseAccessObject.signOut()
    }

    override fun fetchUserById(id: String): LiveData<User?> {
        if (!connectivityManager.isConnected()) {
            authenticatedUserCache.user?.also {
                if (it.id == id) {
                    return MutableLiveData(it)
                }
            }

            val result = MutableLiveData<User?>()
            activity.lifecycleScope.launch {
                friendsCacheDao.getFriendById(id)?.also {
                    result.postValue(it)
                }
            }
            return result
        }
        return firebaseAccessObject.fetchUserById(id, activity)
    }

    override suspend fun addFriend(id: String) {
        firebaseAccessObject.addFriend(id)
    }

    override suspend fun removeFriend(id: String) {
        firebaseAccessObject.removeFriend(id)
    }

    override suspend fun getFriends(): List<User> {
        if (!connectivityManager.isConnected()) {
            return friendsCacheDao.getFriends()
        }

        return firebaseAccessObject.getFriends().also {
            friendsCacheDao.addFriends(it)
        }
    }

    override suspend fun getPostsByUserId(id: String): List<Post> {
        if (!connectivityManager.isConnected()) {
            return postCacheDao.getPostByUserId(id)
        }

        return firebaseAccessObject.getPostsByUserID(id)
    }

    override suspend fun downloadPost(post: Post): Post {
        postCacheDao.getPostById(post.id)?.let {
            return it
        }

        return firebaseAccessObject.downloadPost(post).also {
            postCacheDao.addPost(it)
        }
    }

    override suspend fun post(url: String) {
        firebaseAccessObject.post(url)
    }

    override suspend fun deletePost(postId: String) {
        firebaseAccessObject.deletePost(postId)
    }

    override suspend fun deleteStory(storyId: String) {
        firebaseAccessObject.deleteStory(storyId)
    }

    override suspend fun postStory(url: String) {
        firebaseAccessObject.postStory(url)
    }

    override suspend fun togglePostLike(post: Post) {
        firebaseAccessObject.togglePostLike(post)
    }

    override suspend fun isPostLiked(post: Post): Boolean {
        return firebaseAccessObject.isPostLiked(post)
    }

    override suspend fun postComment(postId: String, comment: String) {
        firebaseAccessObject.postComment(postId, comment)
    }

    override suspend fun toggleCommentLike(comment: Comment) {
        firebaseAccessObject.toggleCommentLike(comment)
    }

    override suspend fun isCommentLiked(comment: Comment): Boolean {
        return firebaseAccessObject.isCommentLiked(comment)
    }

    override suspend fun getStoriesByUserId(id: String): List<Story> {
        return firebaseAccessObject.getStoriesByUserID(id)
    }

    override suspend fun downloadStory(story: Story): Story {
        return firebaseAccessObject.downloadStory(story)
    }

    override fun startLiveLocation() {
        activity.lifecycleScope.launch {
            var liveLocation: LiveData<Location?>? = null

            while (liveLocation == null) {
                delay(1000)
                liveLocation = locationService.getLiveLocation(activity)
            }

            liveLocation.observe(activity) {
                if (it != null) {
                    firebaseAccessObject.uploadLocation(it.latitude, it.longitude)
                }
            }
        }
    }


    override suspend fun getComments(id: String): List<Comment> {
        return firebaseAccessObject.getComments(id)
    }
    
    override fun enableNfc() {
        nfcService.enable(activity)
    }

    override suspend fun getNfcTagById(id : String): Tag? {
        return firebaseAccessObject.getTagById(id)
    }

    override suspend fun writeNfcTag(tag : Tag) {
        firebaseAccessObject.writeTag(tag)
    }

    override fun getLiveNfcTagId(): LiveData<String?> {
        val liveData = MutableLiveData<String?>()
        nfcService.getTag().observe(activity) { id ->
            activity.lifecycleScope.launch(Dispatchers.IO) {
                if (id != null) {
                    val tag = firebaseAccessObject.getTagById(id)
                    if (tag != null) {
                        firebaseAccessObject.visitTag(tag.id)
                    }
                    liveData.postValue(id)
                }
            }
        }
        return liveData
    }

    override suspend fun getAllNfcs(): List<Tag> {
        return firebaseAccessObject.getAllNfcs()
    }

    override suspend fun createNewNfcTag(): Tag? {
        val location = locationService.getLastLocation(activity)
        val owner = getAuthenticatedUser().value
        val id = nfcService.getTag().value
        if (location != null && owner != null && id != null) {
            val tag = Tag(
                id = id,
                name = "New Tag",
                latitude = location.latitude,
                longitude = location.longitude,
                owner = owner,
                visitors = listOf(
                    Pair(Calendar.getInstance().timeInMillis, owner)
                )
            )
            firebaseAccessObject.writeTag(tag)
            return tag
        }
        return null
    }

    override fun getFriendsLocations(): LiveData<Map<String, Triple<Long, Double, Double>>> {
        return firebaseAccessObject.getFriendsLocation(activity)
    }

    override suspend fun getCommentReplies(commentId: String): List<CommentReply> {
        return firebaseAccessObject.getCommentReplies(commentId)
    }

    override suspend fun replyToComment(commentId: String, comment: String) {
        firebaseAccessObject.replyToComment(commentId, comment)
    }

    override suspend fun getPostByIds(userId: String, postId: String): Post? {
        return firebaseAccessObject.getPostByIds(userId, postId)
    }
}
