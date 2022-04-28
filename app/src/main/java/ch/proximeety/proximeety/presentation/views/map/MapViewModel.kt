package ch.proximeety.proximeety.presentation.views.map

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

@HiltViewModel
class MapViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    private val userInteractions: UserInteractions
) : ViewModel() {

    private val _mapLoaded = mutableStateOf(false)
    val mapLoaded: State<Boolean> = _mapLoaded

    private val _friends = mutableStateOf<List<User>>(listOf())
    var friends: State<List<User>> = _friends

    val friendsPosition = userInteractions.getFriendsLocations()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            _friends.value = userInteractions.getFriends()
        }
    }

    fun onEvent(event: MapEvent) {
        when (event) {
            MapEvent.MapLoaded -> _mapLoaded.value = true
        }
    }

}