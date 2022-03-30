package ch.proximeety.proximeety.core.entities

/**
 * Immutable representation of an post.
 */
data class Post(
    /**
     * The unique id representing the post.
     */
    val id: String,
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
)