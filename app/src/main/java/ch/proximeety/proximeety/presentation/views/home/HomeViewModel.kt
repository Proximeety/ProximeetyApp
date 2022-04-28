package ch.proximeety.proximeety.presentation.views.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.proximeety.proximeety.core.entities.Post
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import ch.proximeety.proximeety.presentation.navigation.graphs.AuthenticationNavigationCommands
import ch.proximeety.proximeety.presentation.navigation.graphs.MainNavigationCommands
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The ViewModel for the Home View.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    private val userInteractions: UserInteractions
) : ViewModel() {

    val user = userInteractions.getAuthenticatedUser()

    private val _friendsWithStories = mutableStateOf<List<User>>(listOf())
    var friendsWithStories: State<List<User>> = _friendsWithStories

    private val _posts = mutableStateOf<List<Post>>(listOf())
    var posts: State<List<Post>> = _posts

    private var refreshJob: Job? = null
    private var downloadJob: Job? = null
    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing: State<Boolean> = _isRefreshing

    init {
        refresh()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.NavigateToNearbyUsersViewModel -> {
                navigationManager.navigate(MainNavigationCommands.nearbyUsers)
            }
            HomeEvent.SignOut -> {
                userInteractions.signOut()
                navigationManager.navigate(AuthenticationNavigationCommands.default)
            }
            HomeEvent.NavigateToMapView -> {
                navigationManager.navigate(MainNavigationCommands.map)
            }
            HomeEvent.NavigateToMessagingView -> {
                navigationManager.navigate(MainNavigationCommands.conversationList)
            }
            HomeEvent.NavigateToProfileView -> {
                user.value?.id?.also {
                    navigationManager.navigate(MainNavigationCommands.profileWithArgs(it))
                }
            }
            HomeEvent.NavigateToNearbyUsersView -> {
                navigationManager.navigate(MainNavigationCommands.nearbyUsers)
            }
            HomeEvent.NavigateToUploadView -> {
                navigationManager.navigate(MainNavigationCommands.upload)
            }
            HomeEvent.NavigateToFriendsView -> {
                navigationManager.navigate(MainNavigationCommands.friends)
            }
            HomeEvent.Refresh -> {
                refresh()
            }
            is HomeEvent.DownloadPost -> {
                if (downloadJob == null) {
                    downloadJob = viewModelScope.launch(Dispatchers.IO) {
                        val index = _posts.value.indexOfFirst { it.id == event.post.id }
                        val newList = _posts.value.toMutableList()
                        newList[index] = userInteractions.downloadPost(newList[index]).copy(
                            isLiked = userInteractions.isPostLiked(event.post)
                        )
                        _posts.value = newList.toList()
                        downloadJob = null
                    }
                }
            }
            is HomeEvent.OnStoryClick -> {
                navigationManager.navigate(MainNavigationCommands.storiesWithArgs(event.id))
            }
            is HomeEvent.TogglePostLike -> {
                viewModelScope.launch(Dispatchers.IO) {
                    userInteractions.togglePostLike(event.post)
                }
                val index = _posts.value.indexOfFirst { it.id == event.post.id }
                val newList = _posts.value.toMutableList()
                newList[index] = newList[index].copy(
                    likes = newList[index].likes + if (newList[index].isLiked) -1 else 1,
                    isLiked = !newList[index].isLiked
                )
                _posts.value = newList.toList()
            }
        }
    }

    private fun refresh() {
        refreshJob?.cancel()
        _isRefreshing.value = true
        refreshJob = viewModelScope.launch(Dispatchers.IO) {
            val friendsWithStories = userInteractions.getFriends().filter { it.hasStories }
            val feed = userInteractions.getFeed()
            viewModelScope.launch(Dispatchers.Main) {
                _friendsWithStories.value = friendsWithStories
                _posts.value = feed
                _isRefreshing.value = false
            }
        }
    }
}
