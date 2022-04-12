package ch.proximeety.proximeety.presentation.views.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ch.proximeety.proximeety.core.entities.Comment
import ch.proximeety.proximeety.presentation.theme.spacing


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CommentSection(comments: List<Comment>, onCloseClick: () -> Unit) {
    CommentTopBar(numComments = 10, onCloseClick)
    CommentPostComponent()

    LazyColumn {
        items(comments) {
            CommentComponent(it)
        }
    }
}

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
                text = "User1 · 1h ago",
                fontSize = 6.sp,
                fontWeight = FontWeight.Light
            )

            Text(
                text = "Beatiful Post Yanis.",
                fontSize = 8.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}

@Composable
fun CommentTopBar(numComments: Int, onCloseClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            Text(
                text = "Comments",
                style = MaterialTheme.typography.h5
            )
            Text(
                text = "30",
                style = MaterialTheme.typography.h5
            )
        }

        IconButton(onClick = onCloseClick) {
            Icon(Icons.Outlined.Close, contentDescription = "Close Comment Section")
        }
    }
}

@Composable
fun CommentPostComponent() {
    var text = remember { mutableStateOf("") }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(MaterialTheme.spacing.extraSmall)
            .fillMaxWidth()
            .padding(bottom = 40.dp)
            .clip(MaterialTheme.shapes.medium),
    ) {
        Icon(
            Icons.Outlined.AccountCircle,
            contentDescription = null,
            modifier = Modifier
                .width(40.dp)
                .aspectRatio(1f)
                .clip(CircleShape)
        )
        TextField(
            value = text.value,
            onValueChange = { text.value = it },
            modifier = Modifier.padding(bottom = 8.dp, top = 20.dp),
//            placeholder = { Text(text = "Type your comment...")}
        )
        Icon(
            Icons.Outlined.Send,
            contentDescription = "Post Comment"
        )
    }
}