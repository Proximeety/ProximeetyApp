package ch.proximeety.proximeety.presentation.views.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ch.proximeety.proximeety.core.interactions.UserInteractions
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * The ViewModel for the Home View.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userInteractions: UserInteractions
) : ViewModel() {

    private val _state = mutableStateOf(HomeModel(userInteractions.getAuthenticatedUser()))
    val state: State<HomeModel> = _state

    fun onEvent(event: HomeEvent) {
        when (event) {

        }
    }

}
