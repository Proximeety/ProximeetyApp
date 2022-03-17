package ch.proximeety.proximeety.presentation.views.nearbyUsers

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
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

    private val _state =
        mutableStateOf(NearbyUsersModel(user = userInteractions.getAuthenticatedUser()!!))
    val state: State<NearbyUsersModel> = _state

    val nearbyUsers = userInteractions.getNearbyUsers()

    init {
        viewModelScope.launch {
            userInteractions.setAuthenticatedUserVisible()
        }
    }

    fun onEvent(event: NearbyUsersEvent) {
        when (event) {
        }
    }

}
