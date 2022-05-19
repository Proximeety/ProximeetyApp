package ch.proximeety.proximeety.presentation.views.messages

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.proximeety.proximeety.core.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor() : ViewModel() {

    val messages = MutableLiveData(
        listOf(
            msg1,
            msg2
        )
    )

    val user = MutableLiveData(
        listOf(
            User(
                id = "TJepm7a0BVXNOLhrxNqNJhDHk4r1",
                displayName = "Test User"
            )
        )
    )

}
