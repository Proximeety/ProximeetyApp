package ch.proximeety.proximeety.presentation.views.profile

import ch.proximeety.proximeety.core.entities.Post

/**
 * An event from the View to the ViewModel for the Nearby Users View.
 */
sealed class ProfileEvent {
    class DownloadPost(val post: Post) : ProfileEvent()
    object AddAsFriend : ProfileEvent()
    object SignOut : ProfileEvent()
    object NavigateToSettings : ProfileEvent()
    class DeletePost(val post: Post) : ProfileEvent()
}