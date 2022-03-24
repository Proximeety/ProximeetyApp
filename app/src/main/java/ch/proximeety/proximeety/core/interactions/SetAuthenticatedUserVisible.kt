package ch.proximeety.proximeety.core.interactions

import ch.proximeety.proximeety.core.repositories.UserRepository

/**
 * A user interaction used to set the currently authenticated user visible.
 * @param repository the user repository.
 */
class SetAuthenticatedUserVisible(
    private val repository: UserRepository
) {
    suspend operator fun invoke() {
        return repository.setAuthenticatedUserVisible()
    }
}