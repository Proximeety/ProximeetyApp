package ch.proximeety.proximeety.presentation.views.map

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.proximeety.proximeety.core.entities.Tag
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import ch.proximeety.proximeety.presentation.navigation.graphs.MainNavigationCommands
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

    private val _tags = mutableStateOf<List<Tag>>(listOf())
    var tags: State<List<Tag>> = _tags

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _friends.value = userInteractions.getFriends()
            _tags.value = userInteractions.getAllNfcTags()
        }
    }

    fun onEvent(event: MapEvent) {
        when (event) {
            MapEvent.MapLoaded -> _mapLoaded.value = true
            is MapEvent.OnClickNfcTag -> {
                navigationManager.navigate(MainNavigationCommands.nfcWithArgs(event.nfcId, false))
            }
        }
    }

}