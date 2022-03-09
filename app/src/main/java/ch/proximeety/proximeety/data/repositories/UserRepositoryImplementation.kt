package ch.proximeety.proximeety.data.repositories

import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.core.repositories.UserRepository
import ch.proximeety.proximeety.data.sources.FirebaseAccessObject
import ch.proximeety.proximeety.util.SyncActivity

/**
 * Implementation of the user repository user Firebase.
 * @param firebaseAccessObject the object used to access firebase.
 */
class UserRepositoryImplementation(
    private val firebaseAccessObject: FirebaseAccessObject
) : UserRepository {

    override fun getAuthenticatedUser(): User? {
        return firebaseAccessObject.getAuthenticatedUser()
    }

    override suspend fun authenticateWithGoogle(activity: SyncActivity): User? {
        return firebaseAccessObject.authenticateWithGoogle(activity)
    }
}