package ch.proximeety.proximeety.core.interactions

import ch.proximeety.proximeety.core.repositories.UserRepository

/**
 * Enable NFC.
 */
class EnableNfc(
    private val repository: UserRepository,
) {
    operator fun invoke() {
        return repository.enableNfc()
    }
}