package ch.proximeety.proximeety.presentation.views.post

import androidx.lifecycle.ViewModel
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import ch.proximeety.proximeety.presentation.views.friends.FriendsEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * The ViewModel for the Friends View.
 */
@HiltViewModel
class PostViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    private val userInteractions: UserInteractions
) : ViewModel() {



    init {

    }

    fun onEvent(event: FriendsEvent) {
        when (event) {

        }
    }


}
