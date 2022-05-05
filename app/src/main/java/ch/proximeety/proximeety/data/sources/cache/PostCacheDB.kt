package ch.proximeety.proximeety.data.sources.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import ch.proximeety.proximeety.core.entities.Post

/**
 * Post database using for caching posts.
 */
@Database(entities = [Post::class], version = 1)
abstract class PostCacheDB : RoomDatabase() {

    companion object {
        /**
         * The name of the database.
         */
        const val DB_NAME = "post_cache_db"
    }

    /**
     * The associated DAO.
     */
    abstract val dao: PostCacheDao
}