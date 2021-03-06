package ch.proximeety.proximeety.presentation.views.friends.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ch.proximeety.proximeety.presentation.views.friends.FriendsEvent
import ch.proximeety.proximeety.presentation.views.friends.FriendsViewModel

@Composable
fun SearchBar(
    onSearch: (String) -> Unit = {}
) {
    val friendsViewModel: FriendsViewModel = hiltViewModel()
    val hint = "Search..."

    var query by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    val focusManager = LocalFocusManager.current

    Row {
        Box(
            modifier = Modifier
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
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search,
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (query == "") {
                            friendsViewModel.onEvent(FriendsEvent.UpdateSearch(query))
                        }
                        focusManager.clearFocus()
                    }
                )
            )
            if (isHintDisplayed && query == "") {
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
                friendsViewModel.onEvent(FriendsEvent.UpdateSearch(query))
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
