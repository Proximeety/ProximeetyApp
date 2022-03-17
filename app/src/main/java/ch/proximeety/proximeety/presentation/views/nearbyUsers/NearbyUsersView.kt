package ch.proximeety.proximeety.presentation.views.nearbyUsers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * The Nearby Users View.
 */
@Composable
fun NearbyUsersView(
    viewModel: NearbyUsersViewModel = hiltViewModel()
) {
    val nearbyUsers = viewModel.nearbyUsers.observeAsState(listOf())

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            nearbyUsers.value.forEach {
                Text(text = it.displayName)
            }
        }
    }
}
