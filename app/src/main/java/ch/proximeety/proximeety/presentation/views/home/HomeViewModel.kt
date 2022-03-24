package ch.proximeety.proximeety.presentation.views.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import ch.proximeety.proximeety.presentation.navigation.graphs.AuthenticationNavigationCommands
import ch.proximeety.proximeety.presentation.navigation.graphs.MainNavigationCommands
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * The ViewModel for the Home View.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    private val userInteractions: UserInteractions
) : ViewModel() {

    val user = userInteractions.getAuthenticatedUser()

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.NavigateToNearbyUsersViewModel -> {
                navigationManager.navigate(MainNavigationCommands.nearbyUsers)
            }
            HomeEvent.SignOut -> {
                userInteractions.signOut()
                navigationManager.navigate(AuthenticationNavigationCommands.default)
            }
            HomeEvent.NavigateToMapView -> {
                navigationManager.navigate(MainNavigationCommands.map)
            }

            HomeEvent.NavigateToMessagingView -> {
                navigationManager.navigate(MainNavigationCommands.conversationList)
            }

            HomeEvent.NavigateToProfileView -> {
//                navigationManager.navigate(MainNavigationCommands.)
            }

            HomeEvent.NavigateToNearbyUsersView -> {
                navigationManager.navigate(MainNavigationCommands.nearbyUsers)
            }
        }
    }
}
