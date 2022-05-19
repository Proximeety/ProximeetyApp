package ch.proximeety.proximeety.presentation.views.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import ch.proximeety.proximeety.core.entities.Post
import ch.proximeety.proximeety.core.entities.Story
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import ch.proximeety.proximeety.presentation.navigation.graphs.AuthenticationNavigationCommands
import ch.proximeety.proximeety.presentation.navigation.graphs.MainNavigationCommands
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
    savedStateHandle: SavedStateHandle
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

    private val _stories = mutableStateOf<List<Story>>(listOf())
    val stories: State<List<Story>> = _stories

    init {
        savedStateHandle.get<String>("userId")?.also {
                _user.value = userInteractions.fetchUserById(it)
                isAuthenticatedUserProfile = userInteractions.getAuthenticatedUser().value?.id == it

            viewModelScope.launch(Dispatchers.IO) {
                val posts = userInteractions.getPostUserId(it)
                val friends = userInteractions.getFriends()
                val stories = userInteractions.getStoriesByUserId(it)
                viewModelScope.launch(Dispatchers.Main) {
                    _posts.value = posts
                    _friends.value = friends
                    _stories.value = stories
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
            is ProfileEvent.OnStoryClick -> {
                if (stories.value.isNotEmpty()) {
                    user.value.value?.id?.also {
                        navigationManager.navigate(MainNavigationCommands.storiesWithArgs(it))
                    }
                }
            }
             is ProfileEvent.OnOpenDialogClicked -> {
                _showDialog.value = true
            }
            is ProfileEvent.OnCloseDialog -> {
                _showDialog.value = false
            }
        }
    }  
}
