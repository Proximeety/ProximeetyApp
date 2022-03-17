package ch.proximeety.proximeety.presentation.messages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ch.proximeety.proximeety.R

// https://betterprogramming.pub/jetpack-compose-how-to-build-a-messaging-app-e2cdc828c00f

@Composable
fun MessagesListScreenView(
    viewModel: MessagesViewModel = hiltViewModel() //TODO how to use this efficiently here?
) {

    Scaffold(
        topBar = { messagesListTopBar() }
    ) {
        messagesList(list = list, itemClick = itemClick) // TODO list of message + message onClick
    }git
}

@Composable
fun messagesList(list: Any, itemClick: Any) {
    AdapterList(data = list) {
        MessageView(message = it,itemClick = itemClick)
    }
}

@Composable
fun messagesListTopBar() {
    TopAppBar(
        title = {
            Text(
                text = "Messages",
//                modifier = Modifier.weight(1f), //TODO modifier.weight should exist
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold
                )
            )
        },
        actions = {
              IconButton(
                  onClick = {
                            /* TODO */
                  },
                  modifier = Modifier.padding(start = 8.dp, end = 10.dp)) {
                  Icon(
                      imageVector = Icons.Rounded.Search,
                      contentDescription = "",
                      modifier = Modifier.padding(start = 8.dp,end = 10.dp))
              }
            IconButton(
                onClick = {
                    /* TODO */
                },
                modifier = Modifier.padding(start = 8.dp, end = 10.dp)) {
                Icon(
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = "",
                    modifier = Modifier.padding(start = 10.dp, end = 8.dp))
            }
        },
        backgroundColor = Color.White
    )
}


@Composable
fun MessageView(message: MessagesModel,
                itemClick : (message : MessagesModel) -> Unit){
    Row(modifier = Modifier.padding(16.dp)) {
        SenderIcon()
        Column(modifier = Modifier.padding(start = 8.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Sender(sender = message.sender, modifier = Modifier.weight(1f))
                MessageTime(time = message.time)
            }
            ShortMessage(shortMessage = message.message)
        }
    }
}


@Composable
fun MessageTime(time : String){
    Text(
        text = time,
        style = TextStyle(
            color = Color.Gray,
            fontSize = 12.sp
        ),
        maxLines = 1
    )
}

@Composable
fun ShortMessage(shortMessage : String, modifier: Modifier = Modifier){
    Text(
        text = shortMessage,
        style = TextStyle(
            color = Color.DarkGray,
            fontSize = 14.sp
        ),
        modifier = modifier,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun Sender(sender: String, modifier: Modifier = Modifier) {
    Text(
        text = sender,
        style = TextStyle(
            color = Color.Black,
            fontSize = 18.sp
        ),
        modifier = modifier,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}

@Composable
fun SenderIcon() {
    Box(
        backgroundColor = Color.DarkGray,
        modifier = Modifier.clip(CircleShape)
//            .preferredSize(30.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "",
            tint = Color.LightGray,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
fun MessageViewPreview() {
    val message = MessagesModel(
        sender = "Test sender",
        time = "10:00 AM",
        message = "Hello there, how are you?"
    )
    MessageView(message = message,itemClick = {})
}