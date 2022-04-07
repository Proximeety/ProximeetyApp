package ch.proximeety.proximeety.presentation.views.upload

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import ch.proximeety.proximeety.presentation.navigation.graphs.MainNavigationCommands
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The ViewModel for the Home View.
 */
@HiltViewModel
class UploadViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    private val userInteractions: UserInteractions
) : ViewModel() {

    private val _imageUri = mutableStateOf(EMPTY_IMAGE_URI)
    val imageUri: State<Uri> = _imageUri

    fun onEvent(event: UploadEvent) {
        when (event) {
            is UploadEvent.Post -> {
                _imageUri.value.toString().also {
                    viewModelScope.launch(Dispatchers.IO) {
                        userInteractions.post(it)
                    }
                    navigationManager.navigate(MainNavigationCommands.default)
                }
            }
            UploadEvent.PostStory -> {
                _imageUri.value.toString().also {
                    viewModelScope.launch(Dispatchers.IO) {
                        userInteractions.postStory(it)
                    }
                    navigationManager.navigate(MainNavigationCommands.default)
                }
            }
            is UploadEvent.SetPostURI -> {
                _imageUri.value = event.uri
            }
        }
    }
}
