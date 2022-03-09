package ch.proximeety.proximeety.core.interactions

import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.core.repositories.UserRepository

/**
 * A user interaction used to get the currently authenticated user.
 * @param repository the user repository.
 */
class GetAuthenticatedUser(
    private val repository: UserRepository
) {
    operator fun invoke(): User? {
        return repository.getAuthenticatedUser()
    }
}