package ch.proximeety.proximeety.presentation.views.nearbyUsers

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.presentation.theme.spacing
import ch.proximeety.proximeety.presentation.views.nearbyUsers.components.NearbyUser
import ch.proximeety.proximeety.util.SafeArea
import coil.annotation.ExperimentalCoilApi
import kotlin.math.max

/**
 * The Nearby Users View.
 */
@OptIn(ExperimentalCoilApi::class)
@Composable
fun NearbyUsersView(
    viewModel: NearbyUsersViewModel = hiltViewModel()
) {
    val nearbyUsers = viewModel.nearbyUsers.observeAsState(listOf())

    SafeArea {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Searching for nearby users...",
                    style = MaterialTheme.typography.h2
                )
                Spacer(modifier = Modifier.height(spacing.medium))
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    nearbyUsers.value
                        .forEach {
                            NearbyUser(viewModel, it)
                        }
                    for (i in 0 until max(1, 3 - (nearbyUsers.value.size))) {
                        NearbyUser(viewModel = viewModel, user = null)
                    }
                }
            }
        }
    }
}
