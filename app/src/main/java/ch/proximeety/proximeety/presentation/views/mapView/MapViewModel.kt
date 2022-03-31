package ch.proximeety.proximeety.presentation.views.mapView

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    private val userInteractions: UserInteractions
) : ViewModel() {

    private val _state = mutableStateOf(friend1)
    val state: State<MapModel> = _state

    val friends = MutableLiveData(listOf(friend0, friend1, friend2))

    fun onEvent(event: MapEvent) {
        when (event) {
            is MapEvent.MapClick -> {
                //navigationManager.navigate(MainNavigationCommands.home)
            }
        }
    }

}