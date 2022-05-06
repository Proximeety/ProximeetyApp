package ch.proximeety.proximeety.presentation.views.nfc

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.proximeety.proximeety.core.entities.Tag
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import ch.proximeety.proximeety.presentation.navigation.graphs.MainNavigationCommands
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NfcViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    private val userInteractions: UserInteractions,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _isNewTag = mutableStateOf<Boolean?>(null)
    val isNewTag : State<Boolean?> = _isNewTag

    private val _nfcTag = mutableStateOf< Tag?>(null)
    val nfcTag : State<Tag?> = _nfcTag

    init {
        savedStateHandle.get<String>("tagId").also { id ->
            viewModelScope.launch {
                if (id == null) {
                    navigationManager.goBack()
                } else {
                    _nfcTag.value = userInteractions.getNfcTagById(id)
                }

                _isNewTag.value = nfcTag.value == null
            }
        }
    }

    fun onEvent(event: NfcEvent) {
        when (event) {
            is NfcEvent.NavigateToUserProfile -> {
                navigationManager.navigate(MainNavigationCommands.profileWithArgs(event.userId))
            }
            NfcEvent.CreateNewTag -> {
                viewModelScope.launch {
                    _nfcTag.value = userInteractions.createNewNfcTag()
                    _isNewTag.value = false
                }
            }
            NfcEvent.GoBack -> {
                navigationManager.goBack()
            }
        }
    }
}
