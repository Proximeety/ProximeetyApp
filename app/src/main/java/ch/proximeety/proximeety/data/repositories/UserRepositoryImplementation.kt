package ch.proximeety.proximeety.data.repositories

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import ch.proximeety.proximeety.core.entities.Comment
import ch.proximeety.proximeety.core.entities.Post
import ch.proximeety.proximeety.core.entities.Story
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.core.repositories.UserRepository
import ch.proximeety.proximeety.data.sources.BluetoothService
import ch.proximeety.proximeety.data.sources.FirebaseAccessObject
import ch.proximeety.proximeety.data.sources.LocationService
import ch.proximeety.proximeety.util.SyncActivity
import ch.proximeety.proximeety.util.extensions.await
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Implementation of the user repository user Firebase.
 * @param firebaseAccessObject the object used to access firebase.
 */
class UserRepositoryImplementation(
    private val firebaseAccessObject: FirebaseAccessObject,
    private val bluetoothService: BluetoothService,
    private val locationService: LocationService
) : UserRepository {

    private lateinit var activity: SyncActivity

    override fun setActivity(activity: SyncActivity) {
        this.activity = activity
    }

    override fun getAuthenticatedUser(): LiveData<User?> {
        return firebaseAccessObject.getAuthenticatedUser(activity)
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
        return firebaseAccessObject.fetchUserById(id, activity)
    }

    override suspend fun addFriend(id: String) {
        firebaseAccessObject.addFriend(id)
    }

    override suspend fun getFriends(): List<User> {
        return firebaseAccessObject.getFriends()
    }

    override suspend fun getPostsByUserId(id: String): List<Post> {
        return firebaseAccessObject.getPostsByUserID(id)
    }

    override suspend fun downloadPost(post: Post): Post {
        return firebaseAccessObject.downloadPost(post)
    }

    override suspend fun post(url: String) {
        firebaseAccessObject.post(url)
    }

    override suspend fun deletePost(postId: String) {
        firebaseAccessObject.deletePost(postId)
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

    override fun getFriendsLocations(): LiveData<Map<String, Triple<Long, Double, Double>>> {
        return firebaseAccessObject.getFriendsLocation(activity)
    }

}