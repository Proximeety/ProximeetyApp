package ch.proximeety.proximeety.presentation.views.nearbyUsers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import ch.proximeety.proximeety.presentation.navigation.graphs.MainNavigationCommands
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The ViewModel for the Nearby Users View.
 */
@HiltViewModel
class NearbyUsersViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    private val userInteractions: UserInteractions
) : ViewModel() {

    val nearbyUsers = userInteractions.getNearbyUsers()

    init {
        viewModelScope.launch {
            userInteractions.setAuthenticatedUserVisible()
        }
    }

    fun onEvent(event: NearbyUsersEvent) {
        when (event) {
            is NearbyUsersEvent.NavigateToUserProfile -> {
                navigationManager.navigate(MainNavigationCommands.profileWithArgs(event.id))
            }
        }
    }

}
