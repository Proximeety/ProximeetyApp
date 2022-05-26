package ch.proximeety.proximeety.core.interactions

import ch.proximeety.proximeety.core.repositories.UserRepository

/**
 * Remove a friend to the authenticate list.
 * @param repository the user repository.
 */
class RemoveFriend(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: String) {
        repository.removeFriend(id)
    }
}