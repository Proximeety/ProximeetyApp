package ch.proximeety.proximeety.presentation.views.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import ch.proximeety.proximeety.presentation.navigation.graphs.AuthenticationNavigationCommands
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The ViewModel for the Nearby Users View.
 */
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    private val userInteractions: UserInteractions,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var isAuthenticatedUserProfile: Boolean = false

    private val _user = mutableStateOf<LiveData<User?>>(MutableLiveData(null))
    val user: State<LiveData<User?>> = _user

    init {
        savedStateHandle.get<String>("userId")?.also {
            _user.value = userInteractions.fetchUserById(it)
            isAuthenticatedUserProfile = userInteractions.getAuthenticatedUser().value?.id == it
        }
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.AddAsFriend -> {
                user.value.value?.id?.also {
                    viewModelScope.launch {
                        userInteractions.addFriend(it)
                    }
                }
            }
            ProfileEvent.SignOut -> {
                userInteractions.signOut()
                navigationManager.navigate(AuthenticationNavigationCommands.default)
            }
        }
    }

}
