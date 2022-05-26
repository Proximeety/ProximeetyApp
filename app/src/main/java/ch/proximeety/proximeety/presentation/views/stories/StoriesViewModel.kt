package ch.proximeety.proximeety.presentation.views.stories

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import ch.proximeety.proximeety.core.entities.Story
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import ch.proximeety.proximeety.presentation.navigation.graphs.MainNavigationCommands
import ch.proximeety.proximeety.util.Timer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Duration
import javax.inject.Inject

private val TIMER_DURATION = Duration.ofMillis(5000).toMillis()

/**
 * The ViewModel for the Stories View.
 */
@HiltViewModel
class StoriesViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    private val userInteractions: UserInteractions,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var isAuthenticatedUserProfile: Boolean = false

    private val _user = mutableStateOf<LiveData<User?>>(MutableLiveData(null))
    val user: State<LiveData<User?>> = _user

    private var stories = mutableListOf<Story>()

    private val _currentStory = mutableStateOf<Story?>(null)
    val currentStory: State<Story?> = _currentStory

    private val _nextStory = mutableStateOf<Story?>(null)
    val nextStory: State<Story?> = _nextStory

    private val _storyCount = mutableStateOf(0)
    val storyCount: State<Int> = _storyCount

    private val _currentStoryIndex = mutableStateOf(0)
    val currentStoryIndex: State<Int> = _currentStoryIndex

    private val _progress = mutableStateOf(0f)
    val progress: State<Float> = _progress

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

    private val timer =
        object : Timer(TIMER_DURATION, 10) {
            override fun onTick(millisUntilFinished: Long) {
                _progress.value = 1f - (millisUntilFinished.toFloat() / TIMER_DURATION.toFloat())
            }

            override fun onFinish() {
                onEvent(StoriesEvent.NextStory)
                if (nextStory.value != null) {
                    start()
                } else {
                    _progress.value = 0f
                }
            }
        }

    init {
        savedStateHandle.get<String>("userId")?.also { id ->
            _user.value = userInteractions.fetchUserById(id)
            isAuthenticatedUserProfile = userInteractions.getAuthenticatedUser().value?.id == id

            viewModelScope.launch {
                stories = userInteractions.getStoriesByUserId(id).toMutableList()
                _storyCount.value = stories.size
                for (i in (0 until stories.size)) {
                    stories[i] = userInteractions.downloadStory(stories[i])
                    if (i == 0) {
                        _currentStory.value = stories[i]
                        timer.start()
                    }
                    if (i == 1) {
                        _nextStory.value = stories[i]
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }

    fun onEvent(event: StoriesEvent) {
        when (event) {
            StoriesEvent.NextStory -> {
                if (currentStoryIndex.value < stories.size - 1) {
                    _currentStoryIndex.value = currentStoryIndex.value + 1
                    _currentStory.value = stories[currentStoryIndex.value]
                    if (currentStoryIndex.value < stories.size - 1) {
                        _nextStory.value = stories[currentStoryIndex.value]
                    } else {
                        _nextStory.value = null
                    }
                    _progress.value = 0f
                    timer.start()
                } else {
                    navigationManager.goBack()
                }
            }
            StoriesEvent.PreviousStory -> {
                if (currentStoryIndex.value > 0) {
                    if (currentStory.value != null) {
                        _nextStory.value = _currentStory.value
                    }
                    _currentStoryIndex.value = currentStoryIndex.value - 1
                    _currentStory.value = stories[currentStoryIndex.value]
                    if (_nextStory.value == null && currentStoryIndex.value < stories.size - 1) {
                        _nextStory.value = stories[currentStoryIndex.value]
                    }
                    _progress.value = 0f
                    timer.start()
                }
            }
            StoriesEvent.OnClickOnUserPicture -> {
                user.value.value?.id?.also {
                    navigationManager.navigate(MainNavigationCommands.profileWithArgs(it))
                }
            }
            is StoriesEvent.DeleteStory -> {
                viewModelScope.launch(Dispatchers.IO) {
                    userInteractions.deleteStory(event.story!!.id)
                }
                stories.remove(event.story)
                _storyCount.value = stories.size
                if (stories.size == 0) {
                    navigationManager.goBack()
                } else {
                    if (currentStoryIndex.value >= stories.size) {
                        _currentStoryIndex.value = stories.size - 1
                    }
                    _currentStory.value = stories[currentStoryIndex.value]
                    if (_nextStory.value == null && currentStoryIndex.value < stories.size - 1) {
                        _nextStory.value = stories[currentStoryIndex.value]
                    }
                    _progress.value = 0f
                    timer.start()
                }
                _showDialog.value = false
            }
            is StoriesEvent.OnOpenDialogClicked -> {
                _showDialog.value = true
            }
            is StoriesEvent.OnCloseDialog -> {
                _showDialog.value = false
            }
        }
    }
}
