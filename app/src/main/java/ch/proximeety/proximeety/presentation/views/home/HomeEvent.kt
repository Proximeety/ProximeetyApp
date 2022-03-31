package ch.proximeety.proximeety.presentation.views.home

import ch.proximeety.proximeety.core.entities.Post

/**
 * An event from the View to the ViewModel for the Home View.
 */
sealed class HomeEvent {
    class DownloadPost(val post: Post) : HomeEvent()
    class OnStoryClick(val id: String) : HomeEvent()
    object NavigateToMapView : HomeEvent()
    object NavigateToMessagingView : HomeEvent()
    object NavigateToNearbyUsersView : HomeEvent()
    object NavigateToNearbyUsersViewModel : HomeEvent()
    object NavigateToProfileView : HomeEvent()
    object NavigateToUploadView : HomeEvent()
    object Refresh : HomeEvent()
    object SignOut : HomeEvent()
    class TogglePostLike(val post : Post) : HomeEvent()
}