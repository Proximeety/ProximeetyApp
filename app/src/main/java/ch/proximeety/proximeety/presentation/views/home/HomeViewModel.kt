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

    private val _state = mutableStateOf(HomeModel(userInteractions.getAuthenticatedUser()))
    val state: State<HomeModel> = _state

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
//                navigationManager.navigate(MainNavigationCommands.)
            }

            HomeEvent.NavigateToMessagingView -> {
//                navigationManager.navigate(MainNavigationCommands.)
            }

            HomeEvent.NavigateToProfileView -> {
//                navigationManager.navigate(MainNavigationCommands.)
            }

            HomeEvent.NavigateToSearchBarView -> {
//                navigationManager.navigate(MainNavigationCommands.)
            }

            HomeEvent.NavigateToNearbyUsersView -> {
                navigationManager.navigate(MainNavigationCommands.nearbyUsers)
            }
        }
    }
}
