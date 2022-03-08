package ch.proximeety.proximeety.core.interactions

import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.core.repositories.UserRepository
import ch.proximeety.proximeety.util.SyncActivity

/**
 * A user interactions used to authenticate a user with Google.
 * @param repository the user repository.
 */
class AuthenticateWithGoogle(
    private val repository: UserRepository
) {
    suspend operator fun invoke(activity: SyncActivity): User? {
        return repository.authenticateWithGoogle(activity)
    }
}