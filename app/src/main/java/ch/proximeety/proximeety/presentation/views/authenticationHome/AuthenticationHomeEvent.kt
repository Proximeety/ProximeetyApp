package ch.proximeety.proximeety.presentation.views.authenticationHome

/**
 * An event from the View to the ViewModel for the Authentication Home View.
 */
sealed class AuthenticationHomeEvent {
    object AuthenticateWithGoogle : AuthenticationHomeEvent()
}