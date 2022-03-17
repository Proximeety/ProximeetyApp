package ch.proximeety.proximeety.presentation.views.friends.components


import androidx.compose.foundation.Image
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
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.R

@Composable
fun UserCard(user : User){
    Box(Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            elevation = 3.dp
        ) {
            Row(modifier = Modifier.padding(all = 10.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "User profile picture",
                    modifier = Modifier.size(50.dp).clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(20.dp))


                Column {
                    Text(text = user.displayName.toString(), fontSize = 20.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = user.email.toString())
                }


            }
        }
    }
}