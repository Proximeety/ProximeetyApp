package ch.proximeety.proximeety.presentation.views.post

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import ch.proximeety.proximeety.util.SafeArea

@Composable
fun PostView(viewModel: PostViewModel = hiltViewModel()) {
    val post = viewModel.post.value.observeAsState()

    SafeArea() {

    }
}
