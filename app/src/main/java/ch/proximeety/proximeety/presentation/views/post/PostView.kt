package ch.proximeety.proximeety.presentation.views.post

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ch.proximeety.proximeety.util.SafeArea

/**
 * The Post View.
 */
@Composable
fun PostView(viewModel: PostViewModel = hiltViewModel()) {
    SafeArea {
        Column(modifier = Modifier.fillMaxSize()) {
            //UserList(friends, viewModel)
        }
    }
}