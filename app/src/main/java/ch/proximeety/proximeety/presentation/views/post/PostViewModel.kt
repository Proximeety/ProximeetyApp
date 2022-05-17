package ch.proximeety.proximeety.presentation.views.post

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import ch.proximeety.proximeety.core.entities.Comment
import ch.proximeety.proximeety.core.entities.Post
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import ch.proximeety.proximeety.presentation.views.home.HomeEvent
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

    private var _post = mutableStateOf<LiveData<Post?>>(MutableLiveData(null))
    val post: State<LiveData<Post?>> = _post


    private var _comments = mutableStateOf<List<Comment>>(listOf())
    val comments: State<List<Comment>> = _comments

    private var _commentCount = mutableStateOf<Int>(0)
    val commentCount: State<Int> = _commentCount

    private var _commentSectionPostId: String? = null
    private var downloadJob: Job? = null

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
            is PostEvent.DownloadPost -> {
                if (downloadJob == null) {
                    downloadJob = viewModelScope.launch(Dispatchers.IO) {
                        /*TODO*/
                        downloadJob = null
                    }
                }
            }
            is PostEvent.TogglePostLike -> {
                viewModelScope.launch(Dispatchers.IO) {
                    userInteractions.togglePostLike(event.post)
                }
                /*TODO*/
            }
            is PostEvent.OnCommentSectionClick -> {
                /*TODO*/
            }
        }
    }
}

