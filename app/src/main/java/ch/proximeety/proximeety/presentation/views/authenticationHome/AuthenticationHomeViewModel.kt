package ch.proximeety.proximeety.presentation.views.authenticationHome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.proximeety.proximeety.core.interactions.UserInteractions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The ViewModel for the Authentication Home View.
 */
@HiltViewModel
class AuthenticationHomeViewModel @Inject constructor(
    private val userInteractions: UserInteractions
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<AuthenticationHomeUIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: AuthenticationHomeEvent) {
        when (event) {
            is AuthenticationHomeEvent.AuthenticateWithGoogle -> {
                viewModelScope.launch {
                    userInteractions.authenticateWithGoogle(event.activity)?.also {
                        _eventFlow.emit(AuthenticationHomeUIEvent.NavigateToMainNavigation)
                    }
                }
            }
        }
    }
}
