package ch.proximeety.proximeety.core.entities

/**
 * Immutable representation of an Comment's Reply.
 */
data class CommentReply(
    /**
     * The unique id representing the reply.
     */
    val id: String,
    /**
     * The unique id representing the comment that's being replied to.
     */
    val commentId: String,
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
     * The date and time of the reply.
     */
    val timestamp: Long,
    /**
     * The reply's content.
     */
    val commentReply: String
)