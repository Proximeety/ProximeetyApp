package ch.proximeety.proximeety.presentation.views.messagesScreen

import android.widget.EditText
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ch.proximeety.proximeety.R
import ch.proximeety.proximeety.presentation.theme.spacing
import coil.compose.rememberImagePainter
import androidx.compose.material.Text as Text

@Composable
fun MessagesView(
    viewModel: MessageViewModel = hiltViewModel()
) {
    val messages = viewModel.messages.observeAsState(listOf())
    val user = viewModel.user.observeAsState(listOf())

    Scaffold(
        topBar = { MessagesTopBar(viewModel) },
        bottomBar = { MessagesBottomBar() }
    ) {
        LazyColumn(
            reverseLayout = false,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
            items(messages.value) {
                Row() {
                    if (user.value.count() > 0) {
                        Column(
                            horizontalAlignment = if (it.from == user.value[0].id) Alignment.End else Alignment.Start,
//                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = it.value,
                                softWrap = true,
                                fontStyle = FontStyle.Normal,
                                )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MessagesBottomBar() {
    var text = remember { mutableStateOf("")}
    Row(
        modifier = Modifier
            .padding(MaterialTheme.spacing.extraSmall)
            .fillMaxWidth()
            .padding(bottom = 40.dp)
            .clip(MaterialTheme.shapes.medium),
    ) {
        TextField(
            value = text.value,
            onValueChange = {text.value=it},
            label = {Text("Type your Message")},
            modifier = Modifier.padding(bottom = 8.dp, top = 20.dp),
        )
        Button(
            onClick = {},
            modifier = Modifier.padding(bottom = 8.dp, top = 20.dp).fillMaxWidth(),
        ) {
            Text("Send")
        }
    }
}

@Composable
fun MessagesTopBar(viewModel: MessageViewModel = hiltViewModel()) {
    val user = viewModel.user.observeAsState(listOf())
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.padding(top = 30.dp)
            ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background), // TODO load from user in db
                contentDescription = "User Profile Picture",
                modifier = Modifier.size(50.dp)
            );
                if (user.value.count() > 0) {
                    Text(
                        text = user.value[0].displayName,
                        style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold,
                        )
                    )
                }
            }
        },
        actions = {
            IconButton(
                onClick = {
                    /* TODO */
                },
                modifier = Modifier.padding(start = 8.dp, end = 10.dp, top = 30.dp)
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
                modifier = Modifier.padding(start = 8.dp, end = 10.dp, top = 30.dp)
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
