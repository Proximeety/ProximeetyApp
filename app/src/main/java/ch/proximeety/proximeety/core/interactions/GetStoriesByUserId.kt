package ch.proximeety.proximeety.core.interactions

import ch.proximeety.proximeety.core.entities.Story
import ch.proximeety.proximeety.core.repositories.UserRepository

/**
 * A user interaction used to get the stories of an user.
 * @param repository the user repository.
 */
class GetStoriesByUserId(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: String): List<Story> {
        return repository.getStoriesByUserId(id).sortedBy { it.timestamp }
    }
}
