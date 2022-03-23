package ch.proximeety.proximeety.presentation.views.userProfile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * The ViewModel for the User Profile View.
 */
@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    private val userInteractions: UserInteractions
) : ViewModel() {

    private val _state = mutableStateOf(UserProfileModel(userInteractions.getAuthenticatedUser()))
    val state: State<UserProfileModel> = _state

    fun onEvent(event: UserProfileEvent) {
        when (event) {

        }
    }

}