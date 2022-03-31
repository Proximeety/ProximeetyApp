package ch.proximeety.proximeety.presentation.views.messagesScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    private val userInteractions: UserInteractions
) : ViewModel() {

    private val _state = mutableStateOf(msg1) // TODO this will have to be changed into a db query
    val state: State<MessagesModel> = _state

    val messages = MutableLiveData(listOf(msg1, msg2)) //should be msgs from both users to each other, so 2 queries
    val user = MutableLiveData(listOf(User(id = "TJepm7a0BVXNOLhrxNqNJhDHk4r1", displayName = "Test User"))) //TODO also db query

    fun onEvent(event: MessagesEvent) {
        when (event) {

        }
    }
}
