package ch.proximeety.proximeety.presentation.views.profile

import androidx.compose.foundation.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ch.proximeety.proximeety.R
import ch.proximeety.proximeety.core.entities.Post
import ch.proximeety.proximeety.presentation.theme.spacing
import ch.proximeety.proximeety.presentation.views.home.HomeEvent
import ch.proximeety.proximeety.presentation.views.home.components.Post
import ch.proximeety.proximeety.presentation.views.profile.components.ProfileTopBar
import ch.proximeety.proximeety.util.SafeArea
import coil.compose.rememberImagePainter

@Preview
@Composable
fun ProfileView(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val isAuthenticatedUserProfile = viewModel.isAuthenticatedUserProfile
    val user = viewModel.user.value.observeAsState()

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
                    onClick = {viewModel.onEvent(ProfileEvent.NavigateToSettings)})
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
                ProfilePic(user.value?.profilePicture, user.value?.givenName)
                Text(
                    text = user.value?.displayName.toString(),
                    fontSize = 30.sp,
                    modifier = Modifier.padding(top = 15.dp)
                )
                user.value?.bio?.let { UserBio(Modifier.padding(bottom = 20.dp), it) }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                    FriendStat(viewModel.friends.value.count())
                    PostsStat(viewModel.posts.value.count())
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                if (isAuthenticatedUserProfile) {
                    Button(onClick = { viewModel.onEvent(ProfileEvent.SignOut) }) {
                        Text(text = "Sign out")
                    }
                } else {
                    Button(onClick = { viewModel.onEvent(ProfileEvent.AddAsFriend) }) {
                        Text(text = "Add")
                    }
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                Box(modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small)) {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(100.dp),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall) ,
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
                    ) {
                        items(viewModel.posts.value) {
                            SideEffect {
                                if (it.postURL == null) {
                                    viewModel.onEvent(ProfileEvent.DownloadPost(it))
                                }
                            }
                            SinglePost(it)
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun SinglePost(
    post: Post
) {
    Image(
        painter = rememberImagePainter(post.postURL),
        contentScale = ContentScale.Crop,
        contentDescription = "Post",
        modifier = Modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = false)
            .fillMaxSize()
            .clip(MaterialTheme.shapes.medium)
    )
}

@Composable
fun UserBio(
    modifier: Modifier = Modifier,
    bio: String
) {
    Text(
        text = bio,
        lineHeight = 20.sp,
        fontSize = 18.sp,
        color = Color.Gray,
        modifier = modifier
    )
}

@Composable
fun PostsStat(
    count : Int
) {
    val padding = 7.dp
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(CircleShape)
            .border(width = 1.dp, color = Color.Black, shape = CircleShape)
            .size(100.dp)
    ) {
        Text(
            text = count.toString(),
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            modifier = Modifier.padding(top = padding, start = padding, end = padding)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Posts",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = padding, start = padding, end = padding)
        )
    }
}

@Composable
fun FriendStat(
count : Int
) {
    val padding = 7.dp
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(CircleShape)
            .border(width = 1.dp, color = Color.Black, shape = CircleShape)
            .size(100.dp)
    ) {
        Text(
            text = count.toString(),
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            modifier = Modifier.padding(top = padding, start = padding, end = padding)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Friends",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = padding, start = padding, end = padding)
        )
    }
}

@Composable
fun ProfilePic(
    picUrl: String?, displayName: String?
) {
    Image(
        painter = rememberImagePainter(picUrl),
        contentDescription = "Profile picture of $displayName",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(200.dp)
            .border(
                width = 3.dp,
                color = Color.LightGray,
                shape = CircleShape
            )
            .padding(
                horizontal = 4.dp
            )
            .clip(CircleShape)
            .aspectRatio(1f, matchHeightConstraintsFirst = false)
            .background(Color.Gray)
    )
}
