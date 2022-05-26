package ch.proximeety.proximeety.core.interactions

import androidx.lifecycle.LiveData
import ch.proximeety.proximeety.core.entities.Post
import ch.proximeety.proximeety.core.repositories.UserRepository

/**
 * A user interaction used to get certain post of a user.
 * @param repository the user repository.
 */
class GetPostByIds(
    private val repository: UserRepository
) {
    suspend operator fun invoke(userId: String, postId: String): Post? {
        return repository.getPostByIds(userId, postId)
    }
}
