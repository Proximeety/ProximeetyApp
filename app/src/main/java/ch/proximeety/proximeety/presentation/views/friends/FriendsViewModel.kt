package ch.proximeety.proximeety.presentation.views.friends

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
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

    private val _friends = mutableStateOf<List<User>>(listOf())
    var friends: State<List<User>> = _friends

    init {
        showAllFriends()
    }

    fun onEvent(event: FriendsEvent) {
        when (event) {

        }
    }

    private fun showAllFriends(){
        viewModelScope.launch(Dispatchers.IO) {
            _friends.value = userInteractions.getFriends()
        }
    }

    fun updateSearch(newQuery: String) {
        if (newQuery == "") {
            showAllFriends()
        }
        else {
            _friends.value = _friends.value.filter { (it.displayName).toLowerCase()!!.startsWith(newQuery.toLowerCase()) }
        }
    }
}
