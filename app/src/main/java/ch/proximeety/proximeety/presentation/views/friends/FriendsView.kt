package ch.proximeety.proximeety.presentation.views.friends

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ch.proximeety.proximeety.presentation.views.friends.components.SearchBar
import ch.proximeety.proximeety.presentation.views.friends.components.UserList

/**
 * The Friends View.
 */
@Composable
fun FriendsView(viewModel: FriendsViewModel = hiltViewModel()) {
    val context = LocalContext.current

    Column {
        SearchBar()
        UserList(viewModel.state.value)
    }
}

@Preview
@Composable
fun PreviewUserCard(){
    UserList(FriendsModel())
}
