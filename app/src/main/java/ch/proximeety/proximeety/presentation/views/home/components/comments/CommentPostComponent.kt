package ch.proximeety.proximeety.presentation.views.home.components.comments

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.presentation.theme.spacing

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CommentPostComponent(
    picUrl: String?,
    displayName: String?,
    onPostClick: (String) -> Unit
) {
    val placeHolder = "Type your comment..."
    val contentDescription = "Post Comment"

    val text = remember { mutableStateOf("") }

    val keyboard = LocalSoftwareKeyboardController.current

    TextField(
        value = text.value,
        onValueChange = { text.value = it },
        modifier = Modifier
            .padding(bottom = 10.dp, top = 10.dp)
            .fillMaxWidth(),
        placeholder = { Text(text = placeHolder) },
        shape = RectangleShape,
        leadingIcon = { CommentProfilePic(picUrl = picUrl, displayName = displayName) },
        trailingIcon = {
            IconButton(onClick = {
                if (text.value.isNotEmpty()) {
                    onPostClick(text.value)
                    text.value = ""
                    keyboard?.hide()
                }
            }) {
                Icon(Icons.Outlined.Send, contentDescription = contentDescription)
            }
        })

}