package ch.proximeety.proximeety.presentation.views.friends

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import ch.proximeety.proximeety.presentation.views.friends.components.SearchBar
import ch.proximeety.proximeety.presentation.views.friends.components.UserList
import ch.proximeety.proximeety.util.SafeArea

/**
 * The Friends View.
 */
@Composable
fun FriendsView(viewModel: FriendsViewModel = hiltViewModel()) {
    val friends = viewModel.friends.value

    SafeArea {
        Column {
            SearchBar()
            UserList(friends, viewModel)
        }
    }
}
