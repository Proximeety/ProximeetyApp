package ch.proximeety.proximeety.core.interactions

import ch.proximeety.proximeety.core.repositories.UserRepository

/**
 * Start sharing the live location of the authenticated user.
 * @param repository the user repository.
 */
class StartLiveLocation(
    private val repository: UserRepository
) {
    operator fun invoke() {
        return repository.startLiveLocation()
    }
}
