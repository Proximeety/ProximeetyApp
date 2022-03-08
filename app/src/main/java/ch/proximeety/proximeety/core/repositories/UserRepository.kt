package ch.proximeety.proximeety.core.repositories

import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.util.SyncActivity

/**
 * User repository interface.
 */
interface UserRepository {

    /**
     * Get the currently authenticated user.
     */
    fun getAuthenticatedUser(): User?

    /**
     * Authenticate a new user with Google.
     * @param activity the current activity, needed to launch the Google authentication process.
     * @see [ch.proximeety.proximeety.util.extensions.getActivity]
     */
    suspend fun authenticateWithGoogle(activity: SyncActivity): User?
}