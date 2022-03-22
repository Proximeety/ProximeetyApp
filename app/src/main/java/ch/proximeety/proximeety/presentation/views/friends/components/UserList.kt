package ch.proximeety.proximeety.presentation.views.friends.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ch.proximeety.proximeety.presentation.views.friends.FriendsModel

@Composable
fun UserList(model: FriendsModel){

    LazyColumn{
        items(model.users) { user ->
            UserCard(user = user)
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
