package ch.proximeety.proximeety.core.interactions

/**
 * Set of user interactions or uses cases.
 */
data class UserInteractions(
    val getAuthenticatedUser: GetAuthenticatedUser,
    val authenticateWithGoogle: AuthenticateWithGoogle
)