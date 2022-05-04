package ch.proximeety.proximeety.presentation.views.nfc

import androidx.lifecycle.ViewModel
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import ch.proximeety.proximeety.presentation.navigation.graphs.MainNavigationCommands
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NfcViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    private val userInteractions: UserInteractions
) : ViewModel() {

    val nfcTagId = userInteractions.getNfcTag()

    fun onEvent(event: NfcEvent) {
        when (event) {
            is NfcEvent.NavigateToUserProfile -> {
                navigationManager.navigate(MainNavigationCommands.profileWithArgs(event.userId))
            }
        }
    }
}
