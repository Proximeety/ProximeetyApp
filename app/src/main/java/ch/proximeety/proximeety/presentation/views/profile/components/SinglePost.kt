package ch.proximeety.proximeety.presentation.views.profile.components

import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ch.proximeety.proximeety.core.entities.Post
import ch.proximeety.proximeety.core.entities.User
import coil.compose.rememberImagePainter

@Composable
fun SinglePost(
    post: Post,
    isAuthenticatedUserProfile: Boolean
) {
    var context = LocalContext.current

    Column(){
        Image(
            painter = rememberImagePainter(post.postURL),
            contentScale = ContentScale.Crop,
            contentDescription = "Post",
            modifier = Modifier
                .aspectRatio(1f, matchHeightConstraintsFirst = false)
                .fillMaxSize()
                .clip(MaterialTheme.shapes.medium)
        )
        if (isAuthenticatedUserProfile) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                ButtonExtended()

            }
        }

    }
}

@Composable
fun ButtonExtended(){
    val menuExpanded = remember{ mutableStateOf(false) }

    IconButton( onClick = { menuExpanded.value = true } ) {
        Icon(imageVector = Icons.Rounded.MoreHoriz, contentDescription = "More")
    }

    Column() {
        DropdownMenu(
            expanded = menuExpanded.value,
            onDismissRequest = {
                menuExpanded.value = false
            },
            modifier = Modifier.width(200.dp)
        ) {
            DropdownMenuItem(onClick = {
                menuExpanded.value = false
            }) {
                Text(text = "Delete post")
            }
            DropdownMenuItem(onClick = {  }) {
                Text(text = "Details")
            }
        }
    }
}