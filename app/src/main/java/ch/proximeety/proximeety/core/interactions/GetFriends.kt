package ch.proximeety.proximeety.core.interactions

import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.core.repositories.UserRepository

/**
 * A user interaction used to get the friends of a users
 * @param repository the user repository.
 */
class GetFriends(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): List<User> {
        return repository.getFriends()
    }
}