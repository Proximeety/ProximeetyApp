package ch.proximeety.proximeety.core.entities

/**
 * Immutable representation of an story.
 */
data class Story(
    /**
     * The unique id representing the story.
     */
    val id: String,
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
     * The date and time of the story.
     */
    val timestamp: Long,
    /**
     * The URL of the story image.
     */
    val storyURL: String? = null,
)