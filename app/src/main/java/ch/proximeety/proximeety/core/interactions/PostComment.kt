package ch.proximeety.proximeety.core.interactions

import ch.proximeety.proximeety.core.entities.Comment
import ch.proximeety.proximeety.core.repositories.UserRepository

/**
 * A user interaction used to leave a comment under a post.
 * @param repository the user repository.
 */
class PostComment(
    private val repository: UserRepository
) {
    suspend operator fun invoke(postId: String, comment: String) {
        return repository.postComment(postId, comment)
    }
}