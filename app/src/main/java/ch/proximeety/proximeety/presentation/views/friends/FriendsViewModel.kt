package ch.proximeety.proximeety.presentation.views.friends

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import ch.proximeety.proximeety.presentation.navigation.graphs.MainNavigationCommands
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The ViewModel for the Friends View.
 */
@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    private val userInteractions: UserInteractions
) : ViewModel() {

    private var _allFriends = listOf<User>()
    private val _friends = mutableStateOf<List<User>>(listOf())
    val friends: State<List<User>> = _friends

    init {
        viewModelScope.launch(Dispatchers.Main) {
            _allFriends = userInteractions.getFriends().sortedWith(compareBy { it.givenName })
        }.invokeOnCompletion {
            _friends.value = _allFriends
        }
    }

    fun onEvent(event: FriendsEvent) {
        when (event) {
            is FriendsEvent.OnUserClick -> {
                navigationManager.navigate(MainNavigationCommands.profileWithArgs(event.id))
            }
            is FriendsEvent.UpdateSearch -> {
                if (event.query.isBlank()) {
                    _friends.value = _allFriends
                } else {
                    _friends.value =
                        _allFriends.filter {
                            (it.displayName).lowercase().startsWith(event.query.lowercase())
                        }
                }
            }
        }
    }

}
