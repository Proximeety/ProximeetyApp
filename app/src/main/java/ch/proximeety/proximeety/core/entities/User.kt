package ch.proximeety.proximeety.core.entities

import android.net.Uri
import java.net.URI

/**
 * Immutable representation of an user.
 */
data class User(
    /**
     * The unique id representing the user.
     */
    val id: String,
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
    val profilePicture : String? = null
)