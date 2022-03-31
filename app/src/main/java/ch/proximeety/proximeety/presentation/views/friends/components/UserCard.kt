package ch.proximeety.proximeety.presentation.views.friends.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ch.proximeety.proximeety.R
import ch.proximeety.proximeety.core.entities.User
import coil.compose.rememberImagePainter

@Composable
fun UserCard(user: User, onUserClick: (String) -> Unit) {
    Box(Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clickable{onUserClick(user.id)},
            shape = MaterialTheme.shapes.medium,
            elevation = 3.dp
        ) {
            Row(modifier = Modifier.padding(all = 20.dp)) {
                Image(
                    painter = rememberImagePainter(user.profilePicture),
                    contentDescription = "User profile picture",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(20.dp))

                Column {
                    Text(text = user.displayName, fontSize = 20.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = user.email.toString())
                }
            }
        }
    }
}
