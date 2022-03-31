package ch.proximeety.proximeety.presentation.views.profile

/**
 * An event from the View to the ViewModel for the Nearby Users View.
 */
sealed class ProfileEvent {
    object AddAsFriend : ProfileEvent()
    object SignOut : ProfileEvent()
}