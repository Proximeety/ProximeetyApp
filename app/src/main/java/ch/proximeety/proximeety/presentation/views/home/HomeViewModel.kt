package ch.proximeety.proximeety.presentation.views.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.proximeety.proximeety.core.entities.Comment
import ch.proximeety.proximeety.core.entities.CommentReply
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
import java.util.*
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

    private var _commentSectionPostId = mutableStateOf<String?>(null)
    val commentSectionPostId: State<String?> = _commentSectionPostId

    private var _comments = mutableStateOf<Map<String, List<Comment>>>(mapOf())
    val comments: State<Map<String, List<Comment>>> = _comments

    private var _replies = mutableStateOf<Map<String, Map<String, List<CommentReply>>>>(mapOf())
    val replies: State<Map<String, Map<String, List<CommentReply>>>> = _replies

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
                        _comments.value = _comments.value.plus(
                            event.post.id to userInteractions.getComments(event.post.id)
                        )
                    }
                }
            }
            is HomeEvent.OnCommentSectionClick -> {
                _commentSectionPostId.value = event.postId
                refreshComments()
            }
            is HomeEvent.OnStoryClick -> {
                navigationManager.navigate(MainNavigationCommands.storiesWithArgs(event.id))
            }
            is HomeEvent.PostComment -> {
                comments.value[commentSectionPostId.value]?.toMutableList()?.also { newComments ->
                    newComments.add(
                        Comment(
                            id = "",
                            postId = commentSectionPostId.value!!,
                            posterId = user.value?.id!!,
                            userDisplayName = user.value?.displayName!!,
                            userProfilePicture = user.value?.profilePicture,
                            timestamp = Calendar.getInstance().timeInMillis,
                            comment = event.text,
                            likes = 0,
                            replies = 0,
                        )
                    )
                    _comments.value =
                        comments.value.plus(commentSectionPostId.value!! to newComments)
                }
                viewModelScope.launch(Dispatchers.IO) {
                    commentSectionPostId.value?.let { userInteractions.postComment(it, event.text) }
                    val comments = userInteractions.getComments(commentSectionPostId.value!!)
                    viewModelScope.launch(Dispatchers.Main) {
                        _comments.value = _comments.value.plus(
                            commentSectionPostId.value!! to comments
                        )
                    }
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
            is HomeEvent.ToggleCommentLike -> {
                viewModelScope.launch(Dispatchers.IO) {
                    userInteractions.toggleCommentLike(event.comment)
                }
                val index =
                    _comments.value[event.comment.postId]?.indexOfFirst { it.id == event.comment.id }
                val newList = _comments.value[event.comment.postId]?.toMutableList()
                if (index != null && newList != null) {
                    newList[index] = newList[index].copy(
                        likes = newList[index].likes + if (newList[index].isLiked) -1 else 1,
                        isLiked = !newList[index].isLiked
                    )
                    _comments.value = _comments.value.plus(event.comment.postId to newList.toList())
                }
            }
            is HomeEvent.DownloadReplies -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val replies = userInteractions.getCommentReplies(event.comment.id)
                    viewModelScope.launch(Dispatchers.Main) {
                        _replies.value = _replies.value.plus(
                            event.comment.postId to _replies.value.getOrDefault(
                                event.comment.postId,
                                mapOf()
                            ).plus(event.comment.id to replies)
                        )
                    }
                }
            }
            is HomeEvent.PostReply -> {
                if (user.value != null) {
                    val newReplies =
                        _replies.value[event.comment.postId]?.get(event.comment.id)?.toMutableList()
                            ?: mutableListOf()
                    newReplies.add(
                        CommentReply(
                            id = "",
                            commentId = event.comment.id,
                            posterId = user.value?.id!!,
                            userDisplayName = user.value?.displayName!!,
                            userProfilePicture = user.value?.profilePicture!!,
                            timestamp = Calendar.getInstance().timeInMillis,
                            commentReply = event.reply
                        )
                    )
                    _replies.value = _replies.value.plus(
                        event.comment.postId to _replies.value.getOrDefault(
                            event.comment.postId,
                            mapOf()
                        ).plus(event.comment.id to newReplies)
                    )
                    viewModelScope.launch(Dispatchers.IO) {
                        userInteractions.replyToComment(
                            event.comment.postId,
                            event.comment.id,
                            event.reply
                        )
                        viewModelScope.launch(Dispatchers.Main) {
                            val index = _comments.value[event.comment.postId]?.indexOfFirst {
                                it.id == event.comment.id
                            }
                            val newList = _comments.value[event.comment.postId]?.toMutableList()
                            if (index != null && newList != null) {
                                newList[index] =
                                    newList[index].copy(replies = newList[index].replies + 1)
                                _comments.value =
                                    _comments.value.plus(event.comment.postId to newList.toList())
                            }
                            onEvent(HomeEvent.DownloadReplies(event.comment))
                        }
                    }
                }
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
        viewModelScope.launch(Dispatchers.IO) {
            val postId = commentSectionPostId.value
            if (postId != null) {
                val comments = userInteractions.getComments(postId)
                viewModelScope.launch(Dispatchers.Main) {
                    _comments.value = _comments.value.plus(postId to comments)
                    for (comment in comments) {
                        onEvent(HomeEvent.DownloadReplies(comment))
                    }
                }
            }
        }
    }
}
