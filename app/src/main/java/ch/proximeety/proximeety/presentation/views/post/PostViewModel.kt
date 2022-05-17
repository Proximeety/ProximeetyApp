package ch.proximeety.proximeety.presentation.views.post

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import ch.proximeety.proximeety.core.entities.Post
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    private val _post = mutableStateOf<LiveData<Post?>>(MutableLiveData(null))
    val post: State<LiveData<Post?>> = _post


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


        }
    }
}