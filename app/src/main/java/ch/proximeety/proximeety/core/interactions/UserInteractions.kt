package ch.proximeety.proximeety.core.interactions

/**
 * Set of user interactions or uses cases.
 */
data class UserInteractions(
    val setActivity: SetActivity,
    val getAuthenticatedUser: GetAuthenticatedUser,
    val authenticateWithGoogle: AuthenticateWithGoogle,
    val setAuthenticatedUserVisible: SetAuthenticatedUserVisible,
    val getNearbyUsers: GetNearbyUsers,
    val signOut: SignOut,
    val fetchUserById: FetchUserById,
    val addFriend: AddFriend
)