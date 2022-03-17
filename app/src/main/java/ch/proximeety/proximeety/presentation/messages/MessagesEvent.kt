package ch.proximeety.proximeety.presentation.messages

import ch.proximeety.proximeety.presentation.views.authenticationHome.AuthenticationHomeEvent
import ch.proximeety.proximeety.util.SyncActivity

/**
 * An event from the View to the ViewModel for the Messages View.
 */
sealed class MessagesEvent {
    class AuthenticateWithGoogle(val activity: SyncActivity) : MessagesEvent()

}