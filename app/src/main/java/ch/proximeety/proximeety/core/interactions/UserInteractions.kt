package ch.proximeety.proximeety.core.interactions

import ch.proximeety.proximeety.presentation.views.home.HomeEvent

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
    val deleteStory: DeleteStory,
    val togglePostLike: TogglePostLike,
    val isPostLiked: IsPostLiked,
    val postComment: PostComment,
    val getComments: GetComments,
    val isCommentLiked: IsCommentLiked,
    val toggleCommentLike: ToggleCommentLike,
    val setActivity: SetActivity,
    val setAuthenticatedUserVisible: SetAuthenticatedUserVisible,
    val signOut: SignOut,
    val getPostUserId: GetPostUserId,
    val postStory: PostStory,
    val getStoriesByUserId: GetStoriesByUserId,
    val downloadStory: DownloadStory,
    val getFriendsLocations: GetFriendsLocations,
    val startLiveLocation: StartLiveLocation,
    val enableNfc: EnableNfc,
    val getLiveNfcTagId: GetLiveNfcTagId,
    val getNfcTagById: GetNfcTagById,
    val createNewNfcTag: CreateNewNfcTag,
    val writeNfcTag : WriteNfcTag,
    val getAllNfcTags: GetAllNfcTags
)
