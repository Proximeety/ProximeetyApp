package ch.proximeety.proximeety.core.repositories

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.util.SyncActivity

/**
 * User repository interface.
 */
interface UserRepository {

    /**
     * Set the activity for the user repository. The activity will be used for requesting permissions and launching intents.
     */
    fun setActivity(activity: SyncActivity)

    /**
     * Get the currently authenticated user.
     */
    fun getAuthenticatedUser(): LiveData<User?>

    /**
     * Authenticate a new user with Google.
     * @see [ch.proximeety.proximeety.util.extensions.getActivity]
     */
    suspend fun authenticateWithGoogle(): User?

    /**
     * Set the current user visible for other users. He will appear in their nearby users list.
     */
    suspend fun setAuthenticatedUserVisible()

    /**
     * Get the list of nearby users
     */
    fun getNearbyUsers(): LiveData<List<User>>

    /**
     * Logout the user.
     */
    fun signOut()

    /**
     * Fetch an user
     *
     * @param id the id of the user to fetch.
     * @return the user as a LiveData.
     */
    fun fetchUserById(id: String): LiveData<User?>

    /**
     * Add user as friend.
     *
     * @param id the id of the user to add as friend.
     */
    suspend fun addFriend(id: String)
}