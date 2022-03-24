package ch.proximeety.proximeety.presentation.views.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ch.proximeety.proximeety.presentation.views.home.HomeEvent
import ch.proximeety.proximeety.presentation.views.home.HomeViewModel

/**
 * The HomeView's top bar
 */
@Composable
fun HomeTopBar(viewModel: HomeViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
//        .background(color = Color.LightGray),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "map",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(all = 5.dp)
                    .clickable(
                        enabled = true,
                        onClick = { viewModel.onEvent(HomeEvent.NavigateToMapView) }
                    )
            )

            Text(
                text = "Proximeety", modifier = Modifier.padding(all = 5.dp),
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center
            )

            Text(
                text = "msg",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(all = 5.dp)
                    .clickable(
                        enabled = true,
                        onClick = { viewModel.onEvent(HomeEvent.NavigateToMessagingView) }
                    )
            )
        }
    }
}
