package ch.proximeety.proximeety.core.interactions

import androidx.lifecycle.LiveData
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.core.repositories.UserRepository

/**
 * A user interaction used to get the currently authenticated user.
 * @param repository the user repository.
 */
class GetAuthenticatedUser(
    private val repository: UserRepository
) {
    operator fun invoke(): LiveData<User?> {
        return repository.getAuthenticatedUser()
    }
}