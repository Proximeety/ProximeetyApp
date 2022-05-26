package ch.proximeety.proximeety.core.entities

/**
 * Immutable representation of an post.
 */
data class Comment(
    /**
     * The unique id representing the comment.
     */
    val id: String,
    /**
     * The unique id representing the post.
     */
    val postId: String,
    /**
     *  The unique id of the user.
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
     * The comment's content.
     */
    val comment: String,
    /**
     * The number of likes.
     */
    val likes: Int,
    /**
     * Is Comment liked by the authenticated user
     */
    val isLiked: Boolean = false,
    /**
     * The number of replies
     */
    val replies: Int = 0,
)