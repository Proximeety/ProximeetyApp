package ch.proximeety.proximeety.presentation.views.messagesScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ch.proximeety.proximeety.presentation.views.messagesScreen.components.DirectMessageBottomBar
import ch.proximeety.proximeety.presentation.views.messagesScreen.components.DirectMessageTopBar

@Composable
fun MessagesView(
    viewModel: MessageViewModel = hiltViewModel()
) {
    val messages = viewModel.messages.observeAsState(listOf())
    val user = viewModel.user.observeAsState(listOf())

    Scaffold(
        topBar = { DirectMessageTopBar(viewModel) },
        bottomBar = { DirectMessageBottomBar() }
    ) {
        LazyColumn(
            reverseLayout = false,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(messages.value) {
                Row {
                    if (user.value.count() > 0) {
                        Column(
                            horizontalAlignment = if (it.from == user.value[0].id) Alignment.End else Alignment.Start,
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
