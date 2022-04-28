package ch.proximeety.proximeety.data.sources.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ch.proximeety.proximeety.core.entities.User

/**
 * The Database Access Object for the friends database.
 * @see PostCacheDB
 */
@Dao
interface FriendCacheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFriend(friend: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFriends(friends: List<User>)

    @Query("SELECT * FROM users")
    suspend fun getFriends(): List<User>

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getFriendById(id: String): User?

}