package ch.proximeety.proximeety.presentation.views.home.components.comments

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ch.proximeety.proximeety.core.entities.Comment
import ch.proximeety.proximeety.presentation.theme.spacing

@Composable
fun CommentComponent(comment: Comment) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .padding(all = spacing.small)
            .fillMaxWidth()
    ) {
        CommentProfilePic(
            picUrl = comment.userProfilePicture,
            displayName = comment.userDisplayName
        )
        
        Spacer(modifier = Modifier.size(spacing.small))
        
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {

            Text(
                text = comment.userDisplayName,
                style = MaterialTheme.typography.subtitle2
            )

            Text(
                text = comment.comment,
                style = MaterialTheme.typography.body2
            )
        }
    }
}