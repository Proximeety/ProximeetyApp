package ch.proximeety.proximeety.presentation.views.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import ch.proximeety.proximeety.presentation.views.home.components.*
import ch.proximeety.proximeety.util.SafeArea
import ch.proximeety.proximeety.util.extensions.getActivity
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

/**
 * The Home View.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeView(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val friendsWithStories = viewModel.friendsWithStories.value
    val posts = viewModel.posts.value
    val user = viewModel.user.value
    val usersLikedPosts = viewModel.usersLikedPosts.value

    val scope = rememberCoroutineScope()
    val scaffoldState =
        rememberBottomSheetScaffoldState(
            bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
        )

    BackHandler {
        context.getActivity()?.finish()
    }
    SafeArea {
        BottomSheetScaffold(
            topBar = {
                HomeTopBar(viewModel)
            },
            sheetContent = {
                CommentSection(
                    user = user,
                    comments = listOf(),
                    onCloseClick = {
                        scope.launch {
                            if (scaffoldState.bottomSheetState.isExpanded)
                                scaffoldState.bottomSheetState.collapse()
                        }
                    }
                )
            },
            scaffoldState = scaffoldState,
            sheetPeekHeight = 0.dp, sheetElevation = 0.dp
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
                                viewModel.onEvent(HomeEvent.FetchUsersLiked(it))
                            }
                        }
                        Post(
                            post = it,
                            likedUsers = usersLikedPosts[it.id],
                            onLike = {
                                viewModel.onEvent(HomeEvent.TogglePostLike(it))
                            },
                            onCommentClick = {
                                scope.launch {
                                    scaffoldState.bottomSheetState.expand()
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

