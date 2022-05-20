package ch.proximeety.proximeety.presentation.views.post

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import ch.proximeety.proximeety.core.entities.Comment
import ch.proximeety.proximeety.core.entities.Post
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The ViewModel for the Post View.
 */
@HiltViewModel
class PostViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    private val userInteractions: UserInteractions,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val user = userInteractions.getAuthenticatedUser()

    private var _post = mutableStateOf<LiveData<Post?>>(MutableLiveData(null))
    val post: State<LiveData<Post?>> = _post


    private var _comments = mutableStateOf<List<Comment>>(listOf())
    val comments: State<List<Comment>> = _comments

    private var _commentCount = mutableStateOf<Int>(0)
    val commentCount: State<Int> = _commentCount

    private var _commentSectionPostId: String? = null

    private var refreshJob: Job? = null
    private var downloadJob: Job? = null
    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing: State<Boolean> = _isRefreshing

    init {
        savedStateHandle.get<String>("userId")?.also { userId ->
            savedStateHandle.get<String>("postId")?.also { postId ->
                viewModelScope.launch(Dispatchers.IO) {
                    val post = userInteractions.getPostByIds(userId, postId)
                    viewModelScope.launch(Dispatchers.Main) {
                        _post.value = post
                    }
                }
            }
        }
    }

    fun onEvent(event: PostEvent) {
        when (event) {
            PostEvent.RefreshComments -> {
                refreshComments()
            }
            is PostEvent.DownloadPost -> {
                if (downloadJob == null) {
                    downloadJob = viewModelScope.launch(Dispatchers.IO) {
                        _post.value.value?.isLiked = userInteractions.isPostLiked(event.post)
                        _post.value.value?.postURL = userInteractions.downloadPost(event.post).postURL
                        downloadJob = null
                        _commentCount.value = userInteractions.getComments(event.post.id).size
                    }
                }
            }
            is PostEvent.TogglePostLike -> {
                viewModelScope.launch(Dispatchers.IO) {
                    userInteractions.togglePostLike(event.post)
                }
                post.value.value?.likes = post.value.value?.likes?.plus(if (post.value.value?.isLiked == true) -1 else 1)!!
                post.value.value?.isLiked = !post.value.value?.isLiked!!
            }
            is PostEvent.OnCommentSectionClick -> {
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
            is PostEvent.PostComment -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _commentSectionPostId?.let { userInteractions.postComment(it, event.text) }
                }
                if (_commentSectionPostId != null) {
                    _commentCount.value = _commentCount.value.plus(((_commentCount.value ?: 0) + 1))
                }
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
