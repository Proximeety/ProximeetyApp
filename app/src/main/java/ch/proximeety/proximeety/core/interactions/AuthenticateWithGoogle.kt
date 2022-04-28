package ch.proximeety.proximeety.core.interactions

import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.core.repositories.UserRepository

/**
 * A user interactions used to authenticate a user with Google.
 * @param repository the user repository.
 */
class AuthenticateWithGoogle(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): User? {
        return repository.authenticateWithGoogle()
    }
}