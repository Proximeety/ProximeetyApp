package ch.proximeety.proximeety.core.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

/**
 * Immutable representation of an user.
 */
@Entity(tableName = "users")
@Serializable
data class User(
    /**
     * The unique id representing the user.
     */
    @PrimaryKey val id: String,
    /**
     *  The display name of the user.
     */
    val displayName: String,
    /**
     * The given name or first name of the user.
     */
    val givenName: String? = null,
    /**
     * The family name of the user.
     */
    val familyName: String? = null,
    /**
     * The email address of the user.
     */
    val email: String? = null,
    /**
     * The profile picture URL.
     */
    val profilePicture: String? = null,
    /**
     * The bio of the user
     */
    val bio: String? = null,
    /**
     * True if the user has any stories.
     */
    val hasStories: Boolean = false
)