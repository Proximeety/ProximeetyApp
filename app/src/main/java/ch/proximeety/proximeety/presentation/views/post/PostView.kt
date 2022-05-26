package ch.proximeety.proximeety.presentation.views.post

import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import ch.proximeety.proximeety.presentation.components.Post
import ch.proximeety.proximeety.presentation.components.comments.CommentSection
import ch.proximeety.proximeety.presentation.views.home.HomeEvent
import ch.proximeety.proximeety.util.SafeArea
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostView(viewModel: PostViewModel = hiltViewModel()) {
    val post = viewModel.post.value
    var comments = viewModel.comments.value
    val commentCount = viewModel.commentCount.value
    val user = viewModel.user.value

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    SafeArea {
        BottomSheetScaffold(
            sheetContent = {
                CommentSection(
                    user = user,
                    comments = comments,
                    onCloseClick = {
                        scope.launch {
                            if (scaffoldState.bottomSheetState.isExpanded)
                                scaffoldState.bottomSheetState.collapse()
                            comments = listOf()
                        }
                    },
                    onCommentLike = { comment ->
                        viewModel.onEvent(PostEvent.ToggleCommentLike(comment))
                    },
                    onPostClick = {
                    viewModel.viewModelScope.launch(Dispatchers.IO) {
                        viewModel
                            .onEvent(PostEvent.PostComment(it))
                        viewModel.onEvent(PostEvent.RefreshComments)
                    }
                })
            },
            scaffoldState = scaffoldState,
            sheetPeekHeight = 0.dp,
        ) {
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
}
