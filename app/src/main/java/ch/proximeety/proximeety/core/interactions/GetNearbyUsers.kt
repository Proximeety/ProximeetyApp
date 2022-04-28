package ch.proximeety.proximeety.core.interactions

import androidx.lifecycle.LiveData
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.core.repositories.UserRepository

/**
 * A user interaction used to get the nearby users
 * @param repository the user repository.
 */
class GetNearbyUsers(
    private val repository: UserRepository
) {
    operator fun invoke(): LiveData<List<User>> {
        return repository.getNearbyUsers()
    }
}