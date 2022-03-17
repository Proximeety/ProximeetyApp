package ch.proximeety.proximeety.presentation.messages

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import ch.proximeety.proximeety.presentation.navigation.graphs.MainNavigationCommands
import ch.proximeety.proximeety.presentation.views.authenticationHome.AuthenticationHomeEvent
import ch.proximeety.proximeety.presentation.views.home.HomeEvent
import ch.proximeety.proximeety.presentation.views.home.HomeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    private val userInteractions: UserInteractions
) : ViewModel() {

    private val _state = mutableStateOf(HomeModel(userInteractions.getAuthenticatedUser()))
    val state: State<HomeModel> = _state

    fun onEvent(event: MessagesEvent) {
        when (event) {
            is MessagesEvent.AuthenticateWithGoogle -> { // TODO check what this does
                viewModelScope.launch {
                    userInteractions.authenticateWithGoogle(event.activity)?.also {
                        navigationManager.navigate(MainNavigationCommands.default)
                    }
                }
            }
        }
    }

}