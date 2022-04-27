package ch.proximeety.proximeety.core.interactions

import ch.proximeety.proximeety.core.repositories.UserRepository

/**
 * A user interaction used to delete a story.
 * @param repository the user repository.
 */
class DeleteStory(
    private val repository: UserRepository
) {
    suspend operator fun invoke(storyId: String) {
        return repository.deleteStory(storyId)
    }
}
