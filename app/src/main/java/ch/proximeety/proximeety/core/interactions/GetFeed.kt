package ch.proximeety.proximeety.core.interactions

import ch.proximeety.proximeety.core.entities.Post
import ch.proximeety.proximeety.core.repositories.UserRepository

/**
 * A user interaction used to get the feed of the authenticated user.
 * @param repository the user repository.
 */
class GetFeed(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): List<Post> {
        return repository.getFriends().flatMap { user ->
            repository.getPostsByUserId(user.id)
        }.sortedBy { post -> post.timestamp }.reversed()
    }
}
