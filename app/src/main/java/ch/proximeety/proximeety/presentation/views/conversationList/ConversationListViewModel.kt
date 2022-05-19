package ch.proximeety.proximeety.presentation.views.conversationList

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import ch.proximeety.proximeety.presentation.navigation.graphs.MainNavigationCommands
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConversationListViewModel @Inject constructor(
    private val navigationManager: NavigationManager
) : ViewModel() {

    private val _state = mutableStateOf(msg2)
    val state: State<MessagesModel> = _state

    val messages = MutableLiveData(List(150) { msg2 })

    fun onEvent(event: ConversationListEvent) {
        when (event) {
            is ConversationListEvent.ConversationClick -> {
                navigationManager.navigate(MainNavigationCommands.messages)
            }
        }
    }

}