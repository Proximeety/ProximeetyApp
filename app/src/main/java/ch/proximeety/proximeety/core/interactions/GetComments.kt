package ch.proximeety.proximeety.core.interactions

import ch.proximeety.proximeety.core.entities.Comment
import ch.proximeety.proximeety.core.repositories.UserRepository

/**
 * A user interaction used to get the list of comments by postId.
 * @param repository the user repository.
 */
class GetComments(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id : String): List<Comment> {
        return repository.getComments(id)
    }
}