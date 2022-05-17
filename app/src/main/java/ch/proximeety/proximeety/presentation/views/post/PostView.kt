package ch.proximeety.proximeety.presentation.views.post

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ch.proximeety.proximeety.presentation.components.Post
import ch.proximeety.proximeety.util.SafeArea
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostView(viewModel: PostViewModel = hiltViewModel()) {
    val post = viewModel.post.value.observeAsState().value
    val commentCount = viewModel.commentCount.value

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    SafeArea() {
        if (post != null) {
            SideEffect {
                if (post.postURL == null) {
                    viewModel.onEvent(PostEvent.DownloadPost(post))
                }
            }
            Post(
                post = post,
                onLike = {
                    viewModel.onEvent(PostEvent.TogglePostLike(post))
                },
                onCommentClick = {
                    viewModel.onEvent(PostEvent.OnCommentSectionClick(post.id))
                    scope.launch {
                        scaffoldState.bottomSheetState.expand()
                    }
                },
                numComments = commentCount
            )
        }
    }
}
