package ch.proximeety.proximeety.presentation.components.comments

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import ch.proximeety.proximeety.presentation.theme.spacing

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CommentPostComponent(
    picUrl: String?,
    displayName: String?,
    onPostClick: (String) -> Unit
) {
    val text = remember { mutableStateOf("") }

    Row(
        horizontalArrangement = Arrangement.spacedBy(spacing.small),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small)

    ) {
        CommentProfilePic(picUrl = picUrl, displayName = displayName)
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            BasicTextField(
                value = text.value, onValueChange = { text.value = it },
                singleLine = true,
                textStyle = MaterialTheme.typography.body1.copy(
                    color = MaterialTheme.colors.onBackground
                ),
                cursorBrush = SolidColor(MaterialTheme.colors.onBackground),
                keyboardActions = KeyboardActions(
                    onSend = {
                        onPostClick(text.value)
                        text.value = ""
                    }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Send
                ),
                modifier = Modifier.testTag("comment_post_text_field")
            )
            if (text.value.isBlank()) {
                Text(
                    text = "Add a comment...",
                    style = MaterialTheme.typography.body1,
                    color = Color.Gray,
                )
            }
        }
    }
}