package ch.proximeety.proximeety.core.interactions

import ch.proximeety.proximeety.core.repositories.UserRepository

/**
 * A user interactions used to sign out the current user.
 * @param repository the user repository.
 */
class SignOut(
    private val repository: UserRepository
) {
    operator fun invoke() {
        return repository.signOut()
    }
}