package ch.proximeety.proximeety.presentation.views.home.components.comments

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ch.proximeety.proximeety.core.entities.CommentReply
import ch.proximeety.proximeety.presentation.theme.spacing

@Composable
fun ReplyComponent(
    commentReply: CommentReply
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .padding(all = spacing.small)
            .fillMaxWidth()
    ) {
        CommentProfilePic(
            picUrl = commentReply.userProfilePicture,
            displayName = commentReply.userDisplayName
        )

        Spacer(modifier = Modifier.size(spacing.small))

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            Text(
                text = commentReply.userDisplayName,
                style = MaterialTheme.typography.subtitle2
            )

            Spacer(modifier = Modifier.height(spacing.small))

            Text(
                text = commentReply.commentReply,
                style = MaterialTheme.typography.body2
            )
        }
    }
}