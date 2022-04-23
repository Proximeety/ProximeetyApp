package ch.proximeety.proximeety.presentation.views.profile

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.*
import ch.proximeety.proximeety.core.entities.Post
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.core.interactions.DeletePost
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.presentation.navigation.NavigationCommand
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import ch.proximeety.proximeety.presentation.navigation.graphs.AuthenticationNavigationCommands
import ch.proximeety.proximeety.presentation.navigation.graphs.MainNavigationCommands
import ch.proximeety.proximeety.presentation.views.home.HomeEvent
import ch.proximeety.proximeety.presentation.views.upload.EMPTY_IMAGE_URI
import ch.proximeety.proximeety.presentation.views.upload.UploadEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The ViewModel for the Nearby Users View.
 */
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    private val userInteractions: UserInteractions,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var isAuthenticatedUserProfile: Boolean = false

    private val _user = mutableStateOf<LiveData<User?>>(MutableLiveData(null))
    val user: State<LiveData<User?>> = _user

    private var downloadJob: Job? = null
    private val _posts = mutableStateOf<List<Post>>(listOf())
    val posts: State<List<Post>> = _posts

    private val _friends = mutableStateOf<List<User>>(listOf())
    val friends: State<List<User>> = _friends

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()



    init {
        savedStateHandle.get<String>("userId")?.also {
            _user.value = userInteractions.fetchUserById(it)
            isAuthenticatedUserProfile = userInteractions.getAuthenticatedUser().value?.id == it

            viewModelScope.launch(Dispatchers.IO) {
                val posts = userInteractions.getPostUserId(it)
                val friends = userInteractions.getFriends()
                viewModelScope.launch(Dispatchers.Main) {
                    _posts.value = posts
                    _friends.value = friends
                }
            }
        }
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.NavigateToSettings -> {
                navigationManager.navigate(MainNavigationCommands.settings)
            }
            ProfileEvent.AddAsFriend -> {
                user.value.value?.id?.also {
                    viewModelScope.launch {
                        userInteractions.addFriend(it)
                    }
                }
            }
            ProfileEvent.SignOut -> {
                userInteractions.signOut()
                navigationManager.navigate(AuthenticationNavigationCommands.default)
            }
            is ProfileEvent.DownloadPost -> {
                if (downloadJob == null) {
                    downloadJob = viewModelScope.launch(Dispatchers.IO) {
                        val index = _posts.value.indexOfFirst { it.id == event.post.id }
                        val newList = _posts.value.toMutableList()
                        newList[index] = userInteractions.downloadPost(newList[index])
                        _posts.value = newList.toList()
                        downloadJob = null
                    }
                }
            }
            is ProfileEvent.DeletePost -> {
                viewModelScope.launch(Dispatchers.IO) {
                    userInteractions.deletePost(event.post.id)
                }
                _showDialog.value = false
            }
        }
    }

    fun onOpenDialogClicked() {
        _showDialog.value = true
    }


    fun onCloseDialog() {
        _showDialog.value = false
    }

}
