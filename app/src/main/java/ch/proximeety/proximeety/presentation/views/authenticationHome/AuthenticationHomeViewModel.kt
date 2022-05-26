package ch.proximeety.proximeety.presentation.views.authenticationHome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import ch.proximeety.proximeety.presentation.navigation.graphs.MainNavigationCommands
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The ViewModel for the Authentication Home View.
 */
@HiltViewModel
class AuthenticationHomeViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    private val userInteractions: UserInteractions
) : ViewModel() {

    private val _currentPage = MutableStateFlow(0)
    val currentPage: StateFlow<Int> get() = _currentPage

    fun onEvent(event: AuthenticationHomeEvent) {
        when (event) {
            is AuthenticationHomeEvent.AuthenticateWithGoogle -> {
                viewModelScope.launch(Dispatchers.IO) {
                    userInteractions.authenticateWithGoogle()?.also {
                        navigationManager.navigate(MainNavigationCommands.default)
                    }
                }
            }
            is AuthenticationHomeEvent.SetCurrentPage -> {
                _currentPage.value = event.page
            }
        }
    }
}
