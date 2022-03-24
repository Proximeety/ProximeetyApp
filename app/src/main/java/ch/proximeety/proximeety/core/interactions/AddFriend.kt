package ch.proximeety.proximeety.core.interactions

import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.core.repositories.UserRepository

/**
 * Add a friend to the authenticate list.
 * @param repository the user repository.
 */
class AddFriend(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id : String) {
        repository.addFriend(id)
    }
}