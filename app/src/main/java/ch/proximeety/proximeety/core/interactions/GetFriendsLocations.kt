package ch.proximeety.proximeety.core.interactions

import androidx.lifecycle.LiveData
import ch.proximeety.proximeety.core.repositories.UserRepository

/**
 * Get the locations of all friends.
 * @param repository the user repository.
 */
class GetFriendsLocations(
    private val repository: UserRepository
) {
    operator fun invoke(): LiveData<Map<String, Triple<Long, Double, Double>>> {
        return repository.getFriendsLocations()
    }
}
