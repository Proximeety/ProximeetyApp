package ch.proximeety.proximeety.presentation.views.home

import ch.proximeety.proximeety.core.entities.Comment
import ch.proximeety.proximeety.core.entities.Post

/**
 * An event from the View to the ViewModel for the Home View.
 */
sealed class HomeEvent {
    class DownloadPost(val post: Post) : HomeEvent()
    object NavigateToMapView : HomeEvent()
    object NavigateToMessagingView : HomeEvent()
    object NavigateToNearbyUsersView : HomeEvent()
    object NavigateToProfileView : HomeEvent()
    object NavigateToUploadView : HomeEvent()
    object NavigateToFriendsView : HomeEvent()
    class OnStoryClick(val id: String) : HomeEvent()
    class OnCommentSectionClick(val postId: String): HomeEvent()
    class PostComment(val text : String): HomeEvent()
    object Refresh : HomeEvent()
    object RefreshComments: HomeEvent()
    object SignOut : HomeEvent()
    class TogglePostLike(val post: Post) : HomeEvent()
    class ToggleCommentLike(val comment: Comment): HomeEvent()
}
