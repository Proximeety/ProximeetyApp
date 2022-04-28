package ch.proximeety.proximeety.data.sources.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ch.proximeety.proximeety.core.entities.Post

/**
 * The Database Access Object for the Posts.
 * @see PostCacheDB
 */
@Dao
interface PostCacheDao {

    @Query("SELECT * FROM posts WHERE posterId = :posterId")
    suspend fun getPostByUserId(posterId: String): List<Post>

    @Query("SELECT * FROM posts WHERE id = :id")
    suspend fun getPostById(id: String): Post?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPost(post: Post)
}