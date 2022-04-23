package ch.proximeety.proximeety.core.interactions

/**
 * Set of user interactions or uses cases.
 */
data class UserInteractions(
    val addFriend: AddFriend,
    val authenticateWithGoogle: AuthenticateWithGoogle,
    val downloadPost: DownloadPost,
    val fetchUserById: FetchUserById,
    val getAuthenticatedUser: GetAuthenticatedUser,
    val getFeed: GetFeed,
    val getFriends: GetFriends,
    val getNearbyUsers: GetNearbyUsers,
    val post: Post,
    val deletePost: DeletePost,
    val togglePostLike: TogglePostLike,
    val isPostLiked: IsPostLiked,
    val setActivity: SetActivity,
    val setAuthenticatedUserVisible: SetAuthenticatedUserVisible,
    val signOut: SignOut,
    val getPostUserId: GetPostUserId,
    val postStory: PostStory,
    val getStoriesByUserId: GetStoriesByUserId,
    val downloadStory: DownloadStory
)