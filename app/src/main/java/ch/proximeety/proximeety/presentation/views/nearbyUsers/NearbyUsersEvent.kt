package ch.proximeety.proximeety.presentation.views.nearbyUsers

/**
 * An event from the View to the ViewModel for the Nearby Users View.
 */
sealed class NearbyUsersEvent {
    class NavigateToUserProfile(val id: String) : NearbyUsersEvent()
}