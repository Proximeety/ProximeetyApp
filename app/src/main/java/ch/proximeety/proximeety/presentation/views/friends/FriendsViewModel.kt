package ch.proximeety.proximeety.presentation.views.friends

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * The ViewModel for the Friends View.
 */
@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    private val userInteractions: UserInteractions
) : ViewModel() {

    private val _state = mutableStateOf(FriendsModel())
    val state: State<FriendsModel> = _state

    fun onEvent(event: FriendsEvent) {
        when (event) {

        }
    }



}
