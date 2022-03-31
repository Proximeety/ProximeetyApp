package ch.proximeety.proximeety.presentation.views.friends.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.presentation.views.friends.FriendsEvent
import ch.proximeety.proximeety.presentation.views.friends.FriendsModel
import ch.proximeety.proximeety.presentation.views.friends.FriendsViewModel

@Composable
fun UserList(users: List<User>, viewModel: FriendsViewModel) {

    LazyColumn {
        items(users) {
            UserCard(
                user = it
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
