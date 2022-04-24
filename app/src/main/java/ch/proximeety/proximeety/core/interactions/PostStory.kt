package ch.proximeety.proximeety.core.interactions

import ch.proximeety.proximeety.core.repositories.UserRepository

/**
 * A user interaction used to post.
 * @param repository the user repository.
 */
class PostStory(
    private val repository: UserRepository
) {
    suspend operator fun invoke(url: String) {
        return repository.postStory(url)
    }
}