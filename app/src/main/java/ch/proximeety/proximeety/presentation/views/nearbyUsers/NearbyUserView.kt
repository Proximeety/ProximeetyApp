package ch.proximeety.proximeety.presentation.views.nearbyUsers

import android.os.CountDownTimer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ch.proximeety.proximeety.presentation.theme.spacing
import ch.proximeety.proximeety.util.SafeArea
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter

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
            )
            {
                when (nearbyUsers.value.size) {
                    0 -> {
                        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                            CircularProgressIndicator()
                            Text(text = "Looking for friends...", style = MaterialTheme.typography.h5)
                        }
                    }
                    else -> {
                        nearbyUsers.value.forEach {
                            Box(
                                modifier = Modifier
                                    .clip(MaterialTheme.shapes.medium)
                                    .clickable {
                                        viewModel.onEvent(NearbyUsersEvent.NavigateToUserProfile(it.id))
                                    }
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(spacing.extraSmall)
                                ) {
                                    Image(
                                        painter = rememberImagePainter(it.profilePicture),
                                        contentDescription = "Profile picture of ${it.displayName}",
                                        modifier = Modifier
                                            .width(32.dp)
                                            .aspectRatio(1f)
                                            .clip(CircleShape)
                                            .background(
                                                Color.Gray
                                            )
                                    )
                                    Spacer(modifier = Modifier.width(spacing.small))
                                    Text(text = it.displayName, style = MaterialTheme.typography.h5)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
