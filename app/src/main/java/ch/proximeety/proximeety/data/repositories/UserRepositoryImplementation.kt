package ch.proximeety.proximeety.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.core.repositories.UserRepository
import ch.proximeety.proximeety.data.sources.BluetoothService
import ch.proximeety.proximeety.data.sources.FirebaseAccessObject
import ch.proximeety.proximeety.util.SyncActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
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

    override fun getAuthenticatedUser(): User? {
        return firebaseAccessObject.getAuthenticatedUser()
    }

    override suspend fun authenticateWithGoogle(): User? {
        return firebaseAccessObject.authenticateWithGoogle(activity)
    }

    override suspend fun setAuthenticatedUserVisible() {
        getAuthenticatedUser()?.also {
            bluetoothService.advertiseUser(it, activity)
        }
    }

    override fun getNearbyUsers(): LiveData<List<User>> {
        val users = MutableLiveData(listOf<User>())
        GlobalScope.launch(Dispatchers.Main) {
            bluetoothService.scanForUsers(activity).observe(activity) {
                users.postValue(it)
            }
        }
        return users
    }

    override fun signOut() {
        firebaseAccessObject.signOut()
    }
}