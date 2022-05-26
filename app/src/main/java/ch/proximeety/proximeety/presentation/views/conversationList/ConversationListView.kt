package ch.proximeety.proximeety.presentation.views.conversationList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ch.proximeety.proximeety.presentation.theme.spacing
import ch.proximeety.proximeety.util.SafeArea


@Composable
fun ConversationListView(
    viewModel: ConversationListViewModel = hiltViewModel()
) {

    val messages = viewModel.messages.observeAsState(listOf())

    SafeArea {
        Scaffold(
            topBar = { MessagesListTopBar() }
        ) {
            LazyColumn {
                items(messages.value) {
                    Row(
                        modifier = Modifier
                            .padding(spacing.extraSmall)
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.medium)
                            .clickable {
                                viewModel.onEvent(
                                    ConversationListEvent.ConversationClick()
                                )
                            },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(spacing.medium)
                                .clip(CircleShape)
                                .width(40.dp)
                                .aspectRatio(1f)
                                .background(Color.Gray)
                        ) {

                        }
                        Column {
                            Text(it.sender)
                            Text(it.message)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MessagesListTopBar() {
    TopAppBar(
        title = {
            Text(
                text = "Messages",
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
                modifier = Modifier.padding(start = 8.dp, end = 10.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "",
                    modifier = Modifier.padding(start = 8.dp, end = 10.dp)
                )
            }
            IconButton(
                onClick = {
                    /* TODO */
                },
                modifier = Modifier.padding(start = 8.dp, end = 10.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = "",
                    modifier = Modifier.padding(start = 10.dp, end = 8.dp)
                )
            }
        },
        backgroundColor = MaterialTheme.colors.background
    )
}

//
//@Composable
//fun MessageView(
//    message: MessagesModel,
//    itemClick: (message: MessagesModel) -> Unit
//) {
//    Row(modifier = Modifier.padding(16.dp)) {
//        SenderIcon()
//        Column(modifier = Modifier.padding(start = 8.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)) {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Sender(sender = message.sender, modifier = Modifier.weight(1f))
//                MessageTime(time = message.time)
//            }
//            ShortMessage(shortMessage = message.message)
//        }
//    }
//}
//
//
//@Composable
//fun MessageTime(time: String) {
//    Text(
//        text = time,
//        style = TextStyle(
//            color = Color.Gray,
//            fontSize = 12.sp
//        ),
//        maxLines = 1
//    )
//}
//
//@Composable
//fun ShortMessage(shortMessage: String, modifier: Modifier = Modifier) {
//    Text(
//        text = shortMessage,
//        style = TextStyle(
//            color = Color.DarkGray,
//            fontSize = 14.sp
//        ),
//        modifier = modifier,
//        maxLines = 1,
//        overflow = TextOverflow.Ellipsis
//    )
//}
//
//@Composable
//fun Sender(sender: String, modifier: Modifier = Modifier) {
//    Text(
//        text = sender,
//        style = TextStyle(
//            color = Color.Black,
//            fontSize = 18.sp
//        ),
//        modifier = modifier,
//        overflow = TextOverflow.Ellipsis,
//        maxLines = 1
//    )
//}
//
//@Composable
//fun SenderIcon() {
//    Box(
////        backgroundColor = Color.DarkGray,
//        modifier = Modifier.clip(CircleShape)
////            .preferredSize(30.dp)
//    ) {
//        Icon(
//            imageVector = Icons.Default.Person,
//            contentDescription = "",
//            tint = Color.LightGray,
//            modifier = Modifier.fillMaxSize()
//        )
//    }
//}
//
