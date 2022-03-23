package ch.proximeety.proximeety.presentation.views.userProfile

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ch.proximeety.proximeety.R
import ch.proximeety.proximeety.presentation.views.userProfile.components.ProfileTopBar

@Preview
@Composable
fun UserProfileView(
    //viewModel: UserProfileViewModel = hiltViewModel()
) {
    //val context = LocalContext.current

    Column (Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
        ProfileTopBar()
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            ProfilePic()
            Text(text = "FirstName LastName",
                fontSize = 30.sp,
                modifier = Modifier.padding(top = 15.dp))
            UserBio(Modifier.padding(bottom = 20.dp))
            Row (Modifier.fillMaxWidth().padding(horizontal = 70.dp),
                horizontalArrangement = Arrangement.SpaceEvenly) {
                PostsStat()
                FriendStat()
            }
            Row(modifier = Modifier.fillMaxWidth().padding(top = 20.dp)) {
                SinglePost()
                SinglePost()
                SinglePost()
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                SinglePost()
                SinglePost()
                SinglePost()
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                SinglePost()
                SinglePost()
                SinglePost()
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                SinglePost()
                SinglePost()
                SinglePost()
            }
        }
    }
}

@Preview
@Composable
fun SinglePost(
    /*TODO adapt to user*/
) {
    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp.dp
    Image(
        painter = painterResource(R.drawable.post1),
        contentScale = ContentScale.Crop,
        contentDescription = "profile pic",
        modifier = Modifier
            .size(screenWidth/3)
            .border(
                width = 1.dp,
                color = Color.White,
            ).size(50.dp)
            .padding(
                horizontal = 2.dp
            )
            .aspectRatio(1f, matchHeightConstraintsFirst = false)
    )
}

@Composable
fun UserBio (modifier: Modifier = Modifier
    /*TODO adapt to user*/
) {
    Text(text = "Example Bio For User",
    lineHeight = 20.sp,
    fontSize = 18.sp,
    color = Color.Gray,
    modifier = modifier)
}

@Preview
@Composable
fun PostsStat(
    /*TODO adapt to user stats*/
) {
    val padding = 7.dp
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clip(CircleShape)
            .border(width = 1.dp, color = Color.Black, shape = CircleShape)
            .size(100.dp)
    ) {
        Text (
            text = "17",
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            modifier =  Modifier.padding(top = padding, start = padding, end = padding)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text (
            text = "Posts",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = padding, start = padding, end = padding)
        )
    }
}

@Preview
@Composable
fun FriendStat(
    /*TODO adapt to user stats*/
) {
    val padding = 7.dp
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clip(CircleShape)
            .border(width = 1.dp, color = Color.Black, shape = CircleShape)
            .size(100.dp)
    ) {
        Text (
            text = "200",
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            modifier =  Modifier.padding(top = padding, start = padding, end = padding)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text (
            text = "Friends",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = padding, start = padding, end = padding)
        )
    }
}

@Preview
@Composable
fun ProfilePic(
    /*TODO adapt to take profile pic*/
) {
    Image(
        painter = painterResource(R.drawable.profilepicture),
        contentScale = ContentScale.Crop,
        contentDescription = "profile pic",
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
    )

}
