package ch.proximeety.proximeety.core.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Immutable representation of an post.
 */
@Entity(tableName = "posts")
data class Post(
    /**
     * The unique id representing the post.
     */
    @PrimaryKey val id: String,
    /**
     *  The display name of the user.
     */
    val posterId: String,
    /**
     *  The display name of the user.
     */
    val userDisplayName: String,
    /**
     * The profile picture URL of the user.
     */
    val userProfilePicture: String?,
    /**
     * The date and time of the post.
     */
    val timestamp: Long,
    /**
     * The URL of the post image.
     */
    val postURL: String? = null,
    /**
     * The number of likes.
     */
    val likes: Int,
    /**
     * True if the post is liked by the authenticated user.
     */
    val isLiked: Boolean = false
)