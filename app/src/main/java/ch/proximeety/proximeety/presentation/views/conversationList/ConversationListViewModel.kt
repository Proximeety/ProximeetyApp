package ch.proximeety.proximeety.presentation.views.messages

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    private val userInteractions: UserInteractions
) : ViewModel() {

    private val _state = mutableStateOf(msg2)
    val state: State<MessagesModel> = _state

    val messages = MutableLiveData(listOf(msg2, msg1))

    fun onEvent(event: ConversationListEvent) {
        when (event) {
        }
    }

}