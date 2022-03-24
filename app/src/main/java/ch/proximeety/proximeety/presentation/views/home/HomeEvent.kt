package ch.proximeety.proximeety.presentation.views.home

/**
 * An event from the View to the ViewModel for the Home View.
 */
sealed class HomeEvent {
    object NavigateToNearbyUsersViewModel : HomeEvent()
    object SignOut : HomeEvent()
    object NavigateToMapView : HomeEvent()
    object NavigateToMessagingView: HomeEvent()
    object NavigateToProfileView: HomeEvent()
    object NavigateToSearchBarView: HomeEvent()
    object NavigateToNearbyUsersView: HomeEvent()
    object NavigateToCameraView: HomeEvent()
}