package ch.proximeety.proximeety.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import ch.proximeety.proximeety.core.entities.Post
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.core.repositories.UserRepository
import ch.proximeety.proximeety.data.sources.BluetoothService
import ch.proximeety.proximeety.data.sources.FirebaseAccessObject
import ch.proximeety.proximeety.util.SyncActivity
import ch.proximeety.proximeety.util.extensions.await
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Implementation of the user repository user Firebase.
 * @param firebaseAccessObject the object used to access firebase.
 */
class UserRepositoryImplementation(
    private val firebaseAccessObject: FirebaseAccessObject,
    private val bluetoothService: BluetoothService
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

    override suspend fun togglePostLike(post: Post) {
        firebaseAccessObject.togglePostLike(post)
    }

    override suspend fun isPostLiked(post: Post) : Boolean {
        return firebaseAccessObject.isPostLiked(post)
    }

}