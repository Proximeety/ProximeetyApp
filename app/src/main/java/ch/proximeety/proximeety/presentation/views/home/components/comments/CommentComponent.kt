package ch.proximeety.proximeety.presentation.views.home.components.comments

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ch.proximeety.proximeety.core.entities.Comment

@Composable
fun CommentComponent(comment: Comment) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .padding(all = 2.dp)
            .fillMaxWidth()
    ) {
        Icon(
            Icons.Outlined.AccountCircle,
            contentDescription = null,
            modifier = Modifier
                .width(40.dp)
                .aspectRatio(1f)
                .clip(CircleShape)
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "${comment.userDisplayName} · ${comment.timestamp} ago",
                fontSize = 6.sp,
                fontWeight = FontWeight.Light
            )

            Text(
                text = comment.comment,
                fontSize = 8.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}