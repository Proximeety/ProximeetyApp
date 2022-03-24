package ch.proximeety.proximeety.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.core.repositories.UserRepository
import ch.proximeety.proximeety.util.SyncActivity

/**
 * Mock Implementation of the user repository.
 */
class UserRepositoryMockImplementation : UserRepository {

    private var user: User? = null

    override fun setActivity(activity: SyncActivity) {}

    override fun getAuthenticatedUser(): LiveData<User?> {
        return MutableLiveData(user)
    }

    override suspend fun authenticateWithGoogle(): User? {
        user = User(id = "testUserId", displayName = "Test User")
        return user
    }

    override suspend fun setAuthenticatedUserVisible() {}

    override fun getNearbyUsers(): LiveData<List<User>> {
        return MutableLiveData(
            listOf(
                User("testUserId1", "User1"),
                User("testUserId2", "User2"),
                User("testUserId3", "User3")
            )
        )
    }

    override fun signOut() {
        user = null
    }

    override fun fetchUserById(id: String): LiveData<User?> {
        return MutableLiveData()
    }

    override suspend fun addFriend(id: String) {
    }
}