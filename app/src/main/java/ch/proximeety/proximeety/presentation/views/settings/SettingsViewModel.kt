package ch.proximeety.proximeety.presentation.views.settings

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import ch.proximeety.proximeety.presentation.navigation.graphs.AuthenticationNavigationCommands
import ch.proximeety.proximeety.presentation.navigation.graphs.MainNavigationCommands
import ch.proximeety.proximeety.presentation.views.profile.ProfileEvent
import ch.proximeety.proximeety.presentation.views.upload.EMPTY_IMAGE_URI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The ViewModel for the Settings View.
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    private val userInteractions: UserInteractions
) : ViewModel() {

    val imageLink = mutableStateOf(EMPTY_PROFILE_PIC)
    val userBio = mutableStateOf(EMPTY_BIO)

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.ChangeProfilePic -> {
                imageLink.value.also {
                    viewModelScope.launch {
                        userInteractions.changeProfilePic(it)
                    }
                }
            }
            is SettingsEvent.setProfilePicLink -> {
                imageLink.value = event.profilePic
            }
            is SettingsEvent.ChangeBio -> {
                userBio.value?.also {
                    viewModelScope.launch {
                        userInteractions.changeUserBio(it)
                    }
                }
            }
            is SettingsEvent.setBio -> {
                userBio.value = event.bio
            }
        }
    }
}