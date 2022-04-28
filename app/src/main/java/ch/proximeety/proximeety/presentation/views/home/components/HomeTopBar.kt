package ch.proximeety.proximeety.presentation.views.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.onEvent(HomeEvent.NavigateToProfileView) }) {
                Icon(imageVector = Icons.Rounded.AccountCircle, contentDescription = "Profile")
            }
            Row {
                IconButton(onClick = { viewModel.onEvent(HomeEvent.NavigateToUploadView) }) {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = "Upload")
                }
                IconButton(onClick = { viewModel.onEvent(HomeEvent.NavigateToFriendsView) }) {
                    Icon(imageVector = Icons.Rounded.Search, contentDescription = "Friends")
                }
                IconButton(onClick = { viewModel.onEvent(HomeEvent.NavigateToNearbyUsersView) }) {
                    Icon(imageVector = Icons.Rounded.NearMe, contentDescription = "Nearby")
                }
                IconButton(onClick = { viewModel.onEvent(HomeEvent.NavigateToMapView) }) {
                    Icon(imageVector = Icons.Rounded.Map, contentDescription = "Map")
                }
                IconButton(onClick = { viewModel.onEvent(HomeEvent.NavigateToMessagingView) }) {
                    Icon(imageVector = Icons.Rounded.Mail, contentDescription = "Messages")
                }
            }
        }
    }
}
