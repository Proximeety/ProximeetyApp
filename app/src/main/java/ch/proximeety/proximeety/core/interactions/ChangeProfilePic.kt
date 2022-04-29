package ch.proximeety.proximeety.core.interactions

import ch.proximeety.proximeety.core.entities.Post
import ch.proximeety.proximeety.core.repositories.UserRepository

/**
 * A user interaction used to change the user's profile picture.
 * @param repository the user repository.
 */
class ChangeProfilePic (
    private val repository: UserRepository
) {
    suspend operator fun invoke(profilePic: String) {
        return repository.changeProfilePic(profilePic)
    }
}