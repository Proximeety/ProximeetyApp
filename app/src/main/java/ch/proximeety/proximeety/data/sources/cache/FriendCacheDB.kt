package ch.proximeety.proximeety.data.sources.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import ch.proximeety.proximeety.core.entities.User

/**
 * Friend database using for caching friends.
 */
@Database(entities = [User::class], version = 1)
abstract class FriendCacheDB : RoomDatabase() {
    companion object {
        /**
         * The name of the database.
         */
        const val DB_NAME = "friend_cache_db"
    }

    /**
     * The associated DAO.
     */
    abstract val dao: FriendCacheDao
}