package ch.proximeety.proximeety.presentation.views.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import ch.proximeety.proximeety.util.extensions.getActivity

/**
 * The Home View.
 */
@Composable
fun HomeView(
    viewModel: HomeViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    BackHandler {
        context.getActivity()?.finish()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome ${viewModel.state.value.user?.displayName}")
        Button(onClick = { viewModel.onEvent(HomeEvent.NavigateToNearbyUsersViewModel) }) {
            Text("Go to nearby users")
        }
        Button(onClick = { viewModel.onEvent(HomeEvent.SignOut) }) {
            Text("Sign out")
        }
    }
}
