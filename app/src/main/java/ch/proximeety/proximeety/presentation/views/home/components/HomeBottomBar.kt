package ch.proximeety.proximeety.presentation.views.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ch.proximeety.proximeety.presentation.views.home.HomeEvent
import ch.proximeety.proximeety.presentation.views.home.HomeViewModel

/**
 * The HomeView's bottom bar
 */
@Composable
fun HomeBottomBar(viewModel: HomeViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
//        .border(color = Color.Black, width = 1.dp)
//        .background(color = Color.LightGray)
    ) {
        Divider(color = Color.Black)
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = "Search",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(all = 5.dp)
                    .clickable(
                        enabled = true,
                        onClick = { viewModel.onEvent(HomeEvent.NavigateToNearbyUsersView) }
                    )
            )

            Text(
                text = "Camera",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(all = 5.dp)
                    .clickable(
                        enabled = true,
                        onClick = { viewModel.onEvent(HomeEvent.NavigateToCameraView) }
                    )
            )

            Text(
                text = "Profile",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(all = 5.dp)
                    .clickable(
                        enabled = true,
                        onClick = { viewModel.onEvent(HomeEvent.NavigateToProfileView) }
                    )
            )
        }
    }
}