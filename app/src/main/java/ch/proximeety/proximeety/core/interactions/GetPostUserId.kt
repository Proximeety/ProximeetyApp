package ch.proximeety.proximeety.core.interactions

import ch.proximeety.proximeety.core.entities.Post
import ch.proximeety.proximeety.core.repositories.UserRepository

/**
 * A user interaction used to get the posts of the authenticated user.
 * @param repository the user repository.
 */
class GetPostUserId(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: String): List<Post> {
        return repository.getPostsByUserId(id).sortedBy { post -> post.timestamp }.reversed()

    }
}
