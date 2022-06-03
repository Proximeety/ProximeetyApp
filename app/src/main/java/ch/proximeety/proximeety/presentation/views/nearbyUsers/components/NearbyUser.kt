package ch.proximeety.proximeety.presentation.views.nearbyUsers.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.presentation.theme.spacing
import ch.proximeety.proximeety.presentation.views.nearbyUsers.NearbyUsersEvent
import ch.proximeety.proximeety.presentation.views.nearbyUsers.NearbyUsersViewModel
import coil.compose.rememberImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun NearbyUser(
    viewModel: NearbyUsersViewModel,
    user: User?
) {
    Box(
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable {
                if (user != null) {
                    viewModel.onEvent(NearbyUsersEvent.NavigateToUserProfile(user.id))
                }
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(spacing.extraSmall)
        ) {
            Image(
                painter = rememberImagePainter(user?.profilePicture),
                contentDescription = "Profile picture of ${user?.displayName}",
                modifier = Modifier
                    .width(32.dp)
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .background(
                        Color.Gray
                    )
                    .placeholder(
                        visible = user == null,
                        highlight = PlaceholderHighlight.shimmer()
                    )
            )
            Spacer(modifier = Modifier.width(spacing.small))
            Text(
                text = user?.displayName.toString(),
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .placeholder(
                        visible = user == null,
                        highlight = PlaceholderHighlight.shimmer()
                    )
                    .then(if (user == null) Modifier.fillMaxWidth(0.5f) else Modifier)
            )
        }
    }
}
