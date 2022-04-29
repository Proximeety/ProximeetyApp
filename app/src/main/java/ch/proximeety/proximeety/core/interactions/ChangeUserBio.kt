package ch.proximeety.proximeety.core.interactions

import ch.proximeety.proximeety.core.repositories.UserRepository

/**
 * A user interaction used to change the user's profile picture.
 * @param repository the user repository.
 */
class ChangeUserBio (
    private val repository: UserRepository
) {
    suspend operator fun invoke(bio: String) {
        return repository.changeUserBio(bio)
    }
}