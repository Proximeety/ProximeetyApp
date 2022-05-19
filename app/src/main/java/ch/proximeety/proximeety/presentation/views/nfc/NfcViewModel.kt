package ch.proximeety.proximeety.presentation.views.nfc

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import ch.proximeety.proximeety.presentation.navigation.graphs.MainNavigationCommands
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NfcViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    userInteractions: UserInteractions
) : ViewModel() {

    val nfcTagId = userInteractions.getNfcTag()

    private val _mapReady = mutableStateOf(false)
    val mapReady : State<Boolean> = _mapReady

    fun onEvent(event: NfcEvent) {
        when (event) {
            is NfcEvent.NavigateToUserProfile -> {
                navigationManager.navigate(MainNavigationCommands.profileWithArgs(event.userId))
            }
            NfcEvent.MapLoaded -> {
                _mapReady.value = true
            }
        }
    }
}
