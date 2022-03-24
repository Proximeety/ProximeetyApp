package ch.proximeety.proximeety.core.interactions

import androidx.lifecycle.LiveData
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.core.repositories.UserRepository

/**
 * A user interaction used to fetch an user by id
 * @param repository the user repository.
 */
class FetchUserById(
    private val repository: UserRepository
) {
    operator fun invoke(id: String): LiveData<User?> {
        return repository.fetchUserById(id)
    }
}