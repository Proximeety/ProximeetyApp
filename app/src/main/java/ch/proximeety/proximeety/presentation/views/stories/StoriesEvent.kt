package ch.proximeety.proximeety.presentation.views.stories

import ch.proximeety.proximeety.core.entities.Post
import ch.proximeety.proximeety.core.entities.Story
import ch.proximeety.proximeety.presentation.views.profile.ProfileEvent

/**
 * An event from the View to the ViewModel for the Stories View.
 */
sealed class StoriesEvent {
    object PreviousStory : StoriesEvent()
    object NextStory : StoriesEvent()
    object OnClickOnUserPicture : StoriesEvent()
    class DeleteStory(val story: Story?) : StoriesEvent()
    object OnOpenDialogClicked: StoriesEvent()
    object OnCloseDialog: StoriesEvent()
}