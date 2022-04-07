package ch.proximeety.proximeety.core.interactions

import ch.proximeety.proximeety.core.entities.Story
import ch.proximeety.proximeety.core.repositories.UserRepository

/**
 * A user interaction used to download a story.
 * @param repository the user repository.
 */
class DownloadStory(
    private val repository: UserRepository
) {
    suspend operator fun invoke(story: Story): Story {
        return repository.downloadStory(story)
    }
}