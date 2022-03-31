package ch.proximeety.proximeety.presentation.views.messagesScreen

import android.widget.EditText
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
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
            reverseLayout = true, // does this mean we need to load the items into the "messages list" in a specific order?
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
            items(messages.value) {
                Row() {
                    Column(
                        horizontalAlignment = if (it.from == user.value[0].id) Alignment.End else Alignment.Start, //TODO must change the if clause to compare to current userId
                    ) {
                        Text(it.value)
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
        modifier = Modifier.fillMaxSize(),
    ) {
        TextField(
            value = text.value,
            onValueChange = {text.value=it},
            label = {Text("Type your Message")},
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}

@Composable
fun MessagesTopBar(viewModel: MessageViewModel = hiltViewModel()) {
    val user = viewModel.user.observeAsState(listOf())
    TopAppBar(
        title = {
            Text(
                text = user.value[0].displayName,
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
