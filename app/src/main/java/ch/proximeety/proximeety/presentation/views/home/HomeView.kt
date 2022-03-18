package ch.proximeety.proximeety.presentation.views.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ch.proximeety.proximeety.presentation.views.home.components.homeBottomBar
import ch.proximeety.proximeety.presentation.views.home.components.homeTopBar
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
    Scaffold(
        topBar = {
            homeTopBar()
        },
        bottomBar = {
            homeBottomBar()
        }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
        ) {
            Text(
                text = "Displaying ${viewModel.state.value.user?.displayName}'s feed",
                modifier = Modifier.padding(all = 5.dp),
                style = MaterialTheme.typography.h3,
                textAlign = TextAlign.Center
            )
            Scaffold(
                topBar = {
                    homeTopBar()
                },
                bottomBar = {
                    homeBottomBar()
                }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                ) {
                    Text(
                        text = "Displaying ${viewModel.state.value.user?.displayName}'s feed",
                        modifier = Modifier.padding(all = 5.dp),
                        style = MaterialTheme.typography.h3,
                        textAlign = TextAlign.Center
                    )
                }
                Divider(color = Color.Black)
            }
        }
    }
}
