package ch.proximeety.proximeety.presentation.views.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ch.proximeety.proximeety.presentation.theme.spacing
import ch.proximeety.proximeety.presentation.views.profile.components.ProfilePicture
import ch.proximeety.proximeety.presentation.views.profile.components.SinglePost
import ch.proximeety.proximeety.presentation.views.profile.components.Stats
import ch.proximeety.proximeety.presentation.views.profile.components.UserBio
import ch.proximeety.proximeety.util.SafeArea

@Preview
@Composable
fun ProfileView(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val isAuthenticatedUserProfile = viewModel.isAuthenticatedUserProfile
    val user = viewModel.user.value.observeAsState()
    val isFriend = viewModel.isFriend.value

    SafeArea {
        Column(
            Modifier
                .fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.End, modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { viewModel.onEvent(ProfileEvent.NavigateToSettings) },
                    modifier = Modifier.testTag("Settings")
                )
                {
                    Icon(imageVector = Icons.Filled.Settings, contentDescription = "Settings")
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfilePicture(
                    url = user.value?.profilePicture,
                    displayName = user.value?.givenName,
                    onStoryClick = { viewModel.onEvent(ProfileEvent.OnStoryClick) }
                )
                Spacer(modifier = Modifier.height(spacing.medium))
                Text(
                    text = user.value?.displayName.toString(),
                    style = MaterialTheme.typography.h1,
                )
                user.value?.bio?.let { UserBio(Modifier.padding(bottom = 20.dp), it) }
                Spacer(modifier = Modifier.height(spacing.small))
                Stats(
                    listOf(
                        Pair("Friends", viewModel.friends.value.size.toString()),
                        Pair("Posts", viewModel.posts.value.size.toString())
                    )
                )
                Spacer(modifier = Modifier.height(spacing.small))
                if (isAuthenticatedUserProfile) {
                    Button(onClick = { viewModel.onEvent(ProfileEvent.SignOut) }) {
                        Text(text = "Sign out")
                    }
                } else if (isFriend) {
                    Button(onClick = { viewModel.onEvent(ProfileEvent.RemoveFriend) }) {
                        Text(text = "Remove")
                    }
                } else {
                    Button(onClick = { viewModel.onEvent(ProfileEvent.AddAsFriend) }) {
                        Text(text = "Add")
                    }
                }
                Spacer(modifier = Modifier.height(spacing.small))
                Box(modifier = Modifier.padding(horizontal = spacing.small)) {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(100.dp),
                        verticalArrangement = Arrangement.spacedBy(spacing.extraSmall),
                        horizontalArrangement = Arrangement.spacedBy(spacing.extraSmall)
                    ) {
                        items(viewModel.posts.value) {
                            SideEffect {
                                if (it.postURL == null) {
                                    viewModel.onEvent(ProfileEvent.DownloadPost(it))
                                }
                            }
                            SinglePost(
                                post = it,
                                viewModel = viewModel,
                                onDelete = { viewModel.onEvent(ProfileEvent.DeletePost(it)) }
                            )
                        }
                    }
                }
            }
        }
    }
}
