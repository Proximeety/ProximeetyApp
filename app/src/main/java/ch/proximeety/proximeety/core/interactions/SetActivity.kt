package ch.proximeety.proximeety.core.interactions

import ch.proximeety.proximeety.core.repositories.UserRepository
import ch.proximeety.proximeety.util.SyncActivity

/**
 * Set the users activity for the repository.
 * @param repository the user repository.
 */
class SetActivity(
    private val repository: UserRepository
) {
    operator fun invoke(activity: SyncActivity) {
        return repository.setActivity(activity)
    }
}