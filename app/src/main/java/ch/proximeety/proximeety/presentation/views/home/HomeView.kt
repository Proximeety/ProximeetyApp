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

        }
        Divider(color = Color.Black)
    }
}

/**
 * The HomeView's top bar
 */
@Composable
fun homeTopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
//        .background(color = Color.LightGray),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .height(50.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "map",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(all = 5.dp)
            )

            Text(
                text = "Proximeety", modifier = Modifier.padding(all = 5.dp),
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center
            )

            Text(
                text = "msg",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(all = 5.dp)
            )
        }
    }
}

/**
 * The HomeView's bottom bar
 */
@Preview
@Composable
fun homeBottomBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
//        .border(color = Color.Black, width = 1.dp)
//        .background(color = Color.LightGray)
    ) {
        Divider(color = Color.Black)
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .height(50.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Search",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(all = 5.dp)
            )

            Text(
                text = "Camera",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(all = 5.dp)
            )

            Text(
                text = "Profile",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(all = 5.dp)
            )
        }
    }
}
