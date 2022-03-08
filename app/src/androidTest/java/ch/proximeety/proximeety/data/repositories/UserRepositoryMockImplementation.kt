package ch.proximeety.proximeety.data.repositories

import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.core.repositories.UserRepository
import ch.proximeety.proximeety.data.sources.FirebaseAccessObject
import ch.proximeety.proximeety.util.SyncActivity

/**
 * Mock Implementation of the user repository.
 */
class UserRepositoryMockImplementation() : UserRepository {

    private var user : User? = null

    override fun getAuthenticatedUser(): User? {
        return user
    }

    override suspend fun authenticateWithGoogle(activity: SyncActivity): User? {
        user = User(displayName = "Test User")
        return user
    }
}