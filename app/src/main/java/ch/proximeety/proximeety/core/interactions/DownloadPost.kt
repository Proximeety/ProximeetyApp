package ch.proximeety.proximeety.core.interactions

import ch.proximeety.proximeety.core.entities.Post
import ch.proximeety.proximeety.core.repositories.UserRepository

/**
 * A user interaction used to download a post.
 * @param repository the user repository.
 */
class DownloadPost(
    private val repository: UserRepository
) {
    suspend operator fun invoke(post: Post): Post {
        return repository.downloadPost(post)
    }
}