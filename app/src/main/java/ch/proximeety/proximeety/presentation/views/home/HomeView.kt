package ch.proximeety.proximeety.presentation.views.home

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import ch.proximeety.proximeety.presentation.theme.spacing
import ch.proximeety.proximeety.presentation.components.Post
import ch.proximeety.proximeety.presentation.views.home.components.HomeTopBar
import ch.proximeety.proximeety.presentation.views.home.components.Stories
import ch.proximeety.proximeety.presentation.components.comments.CommentSection
import ch.proximeety.proximeety.util.SafeArea
import ch.proximeety.proximeety.util.extensions.getActivity
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * The Home View.
 */
@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun HomeView(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val friendsWithStories = viewModel.friendsWithStories.value
    val posts = viewModel.posts.value
    val comments = viewModel.comments.value
    val replies = viewModel.replies.value
    val user = viewModel.user.value

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    BackHandler {
        if (scaffoldState.bottomSheetState.isExpanded) {
            scope.launch(Dispatchers.Main) {
                scaffoldState.bottomSheetState.collapse()
            }
        } else {
            context.getActivity()?.finish()
        }
    }

    val keyboard = LocalSoftwareKeyboardController.current
    LaunchedEffect(scaffoldState.bottomSheetState.isCollapsed) {
        if (scaffoldState.bottomSheetState.isCollapsed) {
            keyboard?.hide()
        }
    }

    SafeArea {
        BottomSheetScaffold(
            topBar = {
                HomeTopBar(viewModel)
            },
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
                        viewModel.onEvent(HomeEvent.ToggleCommentLike(comment))
                    },
                    onPostComment = {
                            viewModel
                                .onEvent(HomeEvent.PostComment(it))
                    },
                    onPostReply = { comment, reply ->
                        viewModel.onEvent(
                            HomeEvent.PostReply(
                                comment,
                                reply
                            )
                        )
                    },
                    onShowRepliesClick = { viewModel.onEvent(HomeEvent.DownloadReplies(it)) },
                )
            },
            scaffoldState = scaffoldState,
            sheetPeekHeight = 0.dp,
            sheetShape = RoundedCornerShape(spacing.medium, spacing.medium, 0.dp, 0.dp),
        ) {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = viewModel.isRefreshing.value),
                onRefresh = { viewModel.onEvent(HomeEvent.Refresh) }) {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                ) {
                    if (friendsWithStories.isNotEmpty()) {
                        item {
                            Stories(
                                users = friendsWithStories,
                                onStoryClick = { id -> viewModel.onEvent(HomeEvent.OnStoryClick(id)) },
                                loading = viewModel.isRefreshing.value
                            )
                        }
                    }
                    items(posts) {
                        SideEffect {
                            if (it.postURL == null) {
                                viewModel.onEvent(HomeEvent.DownloadPost(it))
                            }
                        }
                            Post(
                                post = it,
                                onLike = {
                                    viewModel.onEvent(HomeEvent.TogglePostLike(it))
                                },
                                onCommentClick = {
                                    viewModel.onEvent(HomeEvent.OnCommentSectionClick(it.id))
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
