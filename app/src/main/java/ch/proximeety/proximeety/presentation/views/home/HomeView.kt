package ch.proximeety.proximeety.presentation.views.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import ch.proximeety.proximeety.presentation.views.home.components.HomeTopBar
import ch.proximeety.proximeety.presentation.views.home.components.Post
import ch.proximeety.proximeety.presentation.views.home.components.Stories
import ch.proximeety.proximeety.util.SafeArea
import ch.proximeety.proximeety.util.extensions.getActivity
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

/**
 * The Home View.
 */
@Composable
fun HomeView(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val friends = viewModel.friends.value
    val posts = viewModel.posts.value

    BackHandler {
        context.getActivity()?.finish()
    }
    SafeArea {
        Scaffold(
            topBar = {
                HomeTopBar(viewModel)
            }
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
                    if (friends.isNotEmpty()) {
                        item {
                            Stories(
                                users = friends,
                                onStoryClick = { id -> viewModel.onEvent(HomeEvent.OnStoryClick(id)) },
                                loading = viewModel.isRefreshing.value
                            )
                        }
                    }
                    items(posts) {
                        SideEffect {
                            if (it.postURL == null) {
                                viewModel.onEvent(HomeEvent.DownloadPost(it.id))
                            }
                        }
                        Post(it)
                    }
                }
            }
        }
    }
}

