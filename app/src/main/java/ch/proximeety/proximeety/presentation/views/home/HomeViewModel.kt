package ch.proximeety.proximeety.presentation.views.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.proximeety.proximeety.core.entities.Comment
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
    val friendsWithStories: State<List<User>> = _friendsWithStories

    private val _posts = mutableStateOf<List<Post>>(listOf())
    val posts: State<List<Post>> = _posts

    private var _commentSectionPostId: String? = null

    private var _comments = mutableStateOf<List<Comment>>(listOf())
    val comments: State<List<Comment>> = _comments

    private var _commentCount = mutableStateOf<Map<String, Int>>(mapOf())
    val commentCount: State<Map<String, Int>> = _commentCount

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
            HomeEvent.RefreshComments -> {
                refreshComments()
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
                        _commentCount.value = _commentCount.value.plus(
                            event.post.id to userInteractions.getComments(event.post.id).size
                        )
                    }
                }
            }
            is HomeEvent.OnCommentSectionClick -> {
                _comments.value = listOf()
                _commentSectionPostId = event.postId
                viewModelScope.launch(Dispatchers.IO) {
                    val comments = userInteractions.getComments(event.postId)
                    viewModelScope.launch(Dispatchers.Main) {
                        Log.d("COMMENTS", comments.toString())
                        _comments.value = comments.reversed()
                    }
                }
            }
            is HomeEvent.OnStoryClick -> {
                navigationManager.navigate(MainNavigationCommands.storiesWithArgs(event.id))
            }
            is HomeEvent.PostComment -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _commentSectionPostId?.let { userInteractions.postComment(it, event.text) }
                }
                if (_commentSectionPostId != null) {
                    _commentCount.value = _commentCount.value.plus(_commentSectionPostId!! to ((_commentCount.value[_commentSectionPostId] ?: 0) + 1))
                }
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

    private fun refreshComments() {
        refreshJob?.cancel()
        _isRefreshing.value = true
        refreshJob = viewModelScope.launch(Dispatchers.IO) {
            _comments.value = _commentSectionPostId?.let { userInteractions.getComments(it) }!!
            _isRefreshing.value = false
        }
    }
}
