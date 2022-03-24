package ch.proximeety.proximeety.presentation.views.friends.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import ch.proximeety.proximeety.presentation.views.friends.FriendsViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SearchBar(
    onSearch: (String) -> Unit = {}
){
    val friendsViewModel: FriendsViewModel = hiltViewModel()
    var hint = "Search..."

    var query by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    Row {
        Box(modifier = Modifier
            .fillMaxWidth(0.85f)
            .padding(16.dp)
        ) {
            BasicTextField(
                value = query,
                onValueChange = {
                    query = it
                    onSearch(it)
                },
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(color = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(5.dp, CircleShape)
                    .background(Color.White, CircleShape)
                    .padding(horizontal = 20.dp, vertical = 12.dp)
                    .testTag("textField")
                    .onFocusChanged {
                        isHintDisplayed = !it.isFocused
                    },
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (query == "") {
                            friendsViewModel.updateSearch(query)
                        }
                        focusManager.clearFocus() }
                )
            )
            if (isHintDisplayed && query=="") {
                Text(
                    text = hint,
                    color = Color.LightGray,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                )
            }
        }
        Button(
            onClick =
            {
                friendsViewModel.updateSearch(query)
            },
            shape = CircleShape,
            modifier = Modifier
                .padding(vertical = 12.dp)
                .fillMaxWidth(0.9f)
                .testTag("button")
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search",
                modifier = Modifier
                    .size(ButtonDefaults.IconSize)
            )
        }
    }
}
