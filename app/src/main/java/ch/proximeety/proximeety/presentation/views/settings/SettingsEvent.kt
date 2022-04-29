package ch.proximeety.proximeety.presentation.views.settings

import ch.proximeety.proximeety.core.entities.Post
import ch.proximeety.proximeety.presentation.views.profile.ProfileEvent

/**
 * An event from the View to the ViewModel for the Settings View.
 */
sealed class SettingsEvent {
    class setProfilePicLink(val profilePic: String) : SettingsEvent()
    object ChangeProfilePic : SettingsEvent()
    class setBio(val bio: String) : SettingsEvent()
    object ChangeBio : SettingsEvent()
}