package ch.proximeety.proximeety.core.interactions

import ch.proximeety.proximeety.core.repositories.UserRepository

/**
 * A user interaction used to delete a post.
 * @param repository the user repository.
 */
class DeletePost(
    private val repository: UserRepository
) {
    suspend operator fun invoke(postId: String) {
        return repository.deletePost(postId)
    }
}