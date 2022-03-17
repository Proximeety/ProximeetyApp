package ch.proximeety.proximeety.core.repositories

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
    fun getAuthenticatedUser(): User?

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
}