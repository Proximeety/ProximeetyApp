package ch.proximeety.proximeety.presentation.views.upload

import android.net.Uri

/**
 * An event from the View to the ViewModel for the Upload View.
 */
sealed class UploadEvent {
    class SetPostURI(val uri: Uri) : UploadEvent()
    object Post : UploadEvent()
    object PostStory : UploadEvent()
}