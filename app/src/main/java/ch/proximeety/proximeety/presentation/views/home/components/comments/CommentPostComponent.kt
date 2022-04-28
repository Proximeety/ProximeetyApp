package ch.proximeety.proximeety.presentation.views.home.components.comments

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.presentation.theme.spacing

@Composable
fun CommentPostComponent(user: User?, onPostClick: () -> Unit) {
    val placeHolder = "Type your comment..."
    val contentDescription = "Post Comment"

    val text = remember { mutableStateOf("") }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(MaterialTheme.spacing.extraSmall)
            .fillMaxWidth()
            .padding(bottom = 40.dp)
            .clip(MaterialTheme.shapes.medium),
    ) {
        CommentProfilePic(user?.profilePicture, user?.givenName)
        Spacer(modifier = Modifier.padding(5.dp))
        TextField(
            value = text.value,
            onValueChange = { text.value = it },
            modifier = Modifier.padding(bottom = 10.dp, top = 10.dp),
            placeholder = { Text(text = placeHolder) },
            shape = RectangleShape
        )
        IconButton(onClick = onPostClick) {
            Icon(Icons.Outlined.Send, contentDescription = contentDescription)
        }
    }
}