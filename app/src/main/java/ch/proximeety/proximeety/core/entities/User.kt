package ch.proximeety.proximeety.core.entities

/**
 * Immutable representation of an user.
 */
data class User(
    /**
     *  The display name of the user.
     */
    val displayName: String? = null,
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
     * The unique id representing the user.
     */
    val id: String? = null
)