package ch.proximeety.proximeety.presentation.views.messagesScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ch.proximeety.proximeety.R
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.presentation.theme.spacing
import ch.proximeety.proximeety.presentation.views.profile.components.ProfilePic


@Composable
fun DirectMessageTopBar(user: User) {
    TopAppBar(
        modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small),
        title = {
            Row(
                modifier = Modifier.padding(top = 30.dp)
            ) {
                MessagesProfilePic(
                    picUrl = user.profilePicture,
                    displayName = user.displayName,
                    size = MaterialTheme.spacing.large
                )

                Text(
                    text = user.displayName,
                    style = MaterialTheme.typography.h5
                )

            }
        },
        backgroundColor = MaterialTheme.colors.background
    )
}