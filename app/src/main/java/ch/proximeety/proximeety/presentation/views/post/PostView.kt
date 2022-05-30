package ch.proximeety.proximeety.presentation.views.post

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import ch.proximeety.proximeety.presentation.components.Post
import ch.proximeety.proximeety.presentation.components.comments.CommentSection
import ch.proximeety.proximeety.presentation.theme.spacing
import ch.proximeety.proximeety.presentation.views.home.components.Stories
import ch.proximeety.proximeety.util.SafeArea
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun PostView(viewModel: PostViewModel = hiltViewModel()) {
    val posts = viewModel.posts.value
    val comments = viewModel.comments.value
    val replies = viewModel.replies.value
    val user = viewModel.user.value

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    val keyboard = LocalSoftwareKeyboardController.current
    LaunchedEffect(scaffoldState.bottomSheetState.isCollapsed) {
        if (scaffoldState.bottomSheetState.isCollapsed) {
            keyboard?.hide()
        }
    }

    SafeArea {
        BottomSheetScaffold(
            sheetContent = {
                CommentSection(
                    user = user,
                    comments = comments[viewModel.commentSectionPostId.value] ?: listOf(),
                    replies = replies[viewModel.commentSectionPostId.value] ?: mapOf(),
                    onCloseClick = {
                        scope.launch {
                            if (scaffoldState.bottomSheetState.isExpanded)
                                scaffoldState.bottomSheetState.collapse()
                        }
                    },
                    onCommentLike = { comment ->
                        viewModel.onEvent(PostEvent.ToggleCommentLike(comment))
                    },
                    onPostComment = {
                        viewModel.viewModelScope.launch(Dispatchers.IO) {
                            viewModel
                                .onEvent(PostEvent.PostComment(it))
                            viewModel.onEvent(PostEvent.RefreshComments)
                        }
                    },
                    onPostReply = { comment, reply ->
                        viewModel.onEvent(
                            PostEvent.PostReply(
                                comment,
                                reply
                            )
                        )
                    },
                    onShowRepliesClick = { viewModel.onEvent(PostEvent.DownloadReplies(it)) },
                )
            },
            scaffoldState = scaffoldState,
            sheetPeekHeight = 0.dp,
            sheetShape = RoundedCornerShape(spacing.medium, spacing.medium, 0.dp, 0.dp),
        ) {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = viewModel.isRefreshing.value),
                onRefresh = { viewModel.onEvent(PostEvent.Refresh) }) {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                ) {
                    items(posts) {
                        SideEffect {
                            if (it.postURL == null) {
                                viewModel.onEvent(PostEvent.DownloadPost(it))
                            }
                        }
                        Post(
                            post = it,
                            onLike = {
                                viewModel.onEvent(PostEvent.TogglePostLike(it))
                            },
                            onCommentClick = {
                                viewModel.onEvent(PostEvent.OnCommentSectionClick(it.id))
                                scope.launch {
                                    scaffoldState.bottomSheetState.expand()
                                }
                            },
                            numComments = comments[it.id]?.size ?: 0,
                        )
                    }
                }
            }
        }
    }
}
