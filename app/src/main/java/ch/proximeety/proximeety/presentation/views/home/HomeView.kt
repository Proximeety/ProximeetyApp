package ch.proximeety.proximeety.presentation.views.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ch.proximeety.proximeety.presentation.views.home.components.HomeTopBar
import ch.proximeety.proximeety.presentation.views.home.components.HomeBottomBar
import ch.proximeety.proximeety.util.extensions.getActivity

/**
 * The Home View.
 */
@Composable
fun HomeView(
    viewModel: HomeViewModel = hiltViewModel()
) {

    val user = viewModel.user.observeAsState()

    val context = LocalContext.current

    BackHandler {
        context.getActivity()?.finish()
    }
    Scaffold(
        topBar = {
            HomeTopBar(viewModel)
        },
        bottomBar = {
            HomeBottomBar(viewModel)
        }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
        ) {
            Text(
                text = "Displaying ${user.value?.displayName}'s feed",
                modifier = Modifier.padding(all = 5.dp),
                style = MaterialTheme.typography.h3,
                textAlign = TextAlign.Center
            )
            Button(onClick = {viewModel.onEvent(HomeEvent.SignOut)}) {
                Text("Sign out")
            }
        }
    }
}
