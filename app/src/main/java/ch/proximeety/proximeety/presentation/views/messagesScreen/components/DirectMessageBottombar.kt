package ch.proximeety.proximeety.presentation.views.messagesScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import ch.proximeety.proximeety.presentation.theme.spacing

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DirectMessageBottomBar() {

    val placeHolder = "Type your message..."

    val sendText = "Send"
    val contentDescription = "Send your message"

    val keyboard = LocalSoftwareKeyboardController.current

    var text = remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .padding(MaterialTheme.spacing.extraSmall)
            .fillMaxWidth()
            .padding(bottom = 40.dp)
            .clip(MaterialTheme.shapes.medium),
    ) {
        TextField(
            value = text.value,
            onValueChange = { text.value = it },
            placeholder = { Text(text = placeHolder) },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp, top = 20.dp),
            trailingIcon = {
                Text(
                    text = sendText,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.clickable(
                        onClick = {
                            if (text.value.isNotEmpty()) {
                                TODO("add text send logic")
                                text.value = ""
                                keyboard?.hide()
                            }
                        }),
                    color = MaterialTheme.colors.primary
                )
            }
        )
    }
}