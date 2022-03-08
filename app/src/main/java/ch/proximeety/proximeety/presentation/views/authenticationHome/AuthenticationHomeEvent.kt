package ch.proximeety.proximeety.presentation.views.authenticationHome

import ch.proximeety.proximeety.util.SyncActivity

/**
 * An event from the View to the ViewModel for the Authentication Home View.
 */
sealed class AuthenticationHomeEvent {
    class AuthenticateWithGoogle(val activity: SyncActivity) : AuthenticationHomeEvent()
}