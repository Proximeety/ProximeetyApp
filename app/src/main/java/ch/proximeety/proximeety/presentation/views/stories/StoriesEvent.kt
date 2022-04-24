package ch.proximeety.proximeety.presentation.views.stories

/**
 * An event from the View to the ViewModel for the Stories View.
 */
sealed class StoriesEvent {
    object PreviousStory : StoriesEvent()
    object NextStory : StoriesEvent()
    object OnClickOnUserPicture : StoriesEvent()
}