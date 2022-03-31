package ch.proximeety.proximeety.presentation.views.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
fun ProfileView(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val isAuthenticatedUserProfile = viewModel.isAuthenticatedUserProfile
    val user = viewModel.user.value.observeAsState()

    SafeArea {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            user.value?.also {
                Image(
                    painter = rememberImagePainter(it.profilePicture),
                    contentDescription = "Profile picture of ${it.displayName}",
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .aspectRatio(1f)
                        .clip(CircleShape)
                        .background(
                            Color.Gray
                        )
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
                Text(text = it.displayName, style = MaterialTheme.typography.h1)
                if (!isAuthenticatedUserProfile) {
                    Button(onClick = { viewModel.onEvent(ProfileEvent.AddAsFriend) }) {
                        Text("Add")
                    }
                } else {
                    Button(onClick = { viewModel.onEvent(ProfileEvent.SignOut) }) {
                        Text("Sign out")
                    }
                }
            }
        }
    }
}
