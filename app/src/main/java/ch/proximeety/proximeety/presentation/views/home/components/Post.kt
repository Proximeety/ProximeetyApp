package ch.proximeety.proximeety.presentation.views.home.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Message
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import ch.proximeety.proximeety.core.entities.Post
import ch.proximeety.proximeety.presentation.theme.spacing
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder

@OptIn(ExperimentalCoilApi::class)
@Composable
fun Post(
    post: Post,
    onLike: () -> Unit,
    onCommentClick: () -> Unit,
    numComments: Int
) {
    Column(
        modifier = Modifier
            .padding(spacing.medium)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberImagePainter(post.userProfilePicture),
                    contentDescription = "Profile picture of ${post.userDisplayName}",
                    modifier = Modifier
                        .width(40.dp)
                        .aspectRatio(1f)
                        .clip(CircleShape)
                        .background(
                            Color.Gray
                        )
                )
                Spacer(modifier = Modifier.width(spacing.medium))
                Column {
                    Text(text = post.userDisplayName, style = MaterialTheme.typography.h4)
                    Text(text = "Lausanne", style = MaterialTheme.typography.h5, color = Color.Gray)
                }
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Rounded.MoreHoriz, contentDescription = "More")
            }
        }
        Spacer(modifier = Modifier.height(spacing.small))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(MaterialTheme.shapes.large)
                .placeholder(
                    visible = post.postURL == null,
                    highlight = PlaceholderHighlight.fade()
                )
        ) {
            if (post.postURL != null) {
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = rememberImagePainter(Uri.parse(post.postURL)),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Post image",
                )
            }
        }
        Spacer(modifier = Modifier.height(spacing.small))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onLike) {
                    Icon(
                        imageVector = if (post.isLiked) Icons.Rounded.Favorite
                        else Icons.Rounded.FavoriteBorder,
                        contentDescription = "Like"
                    )
                }
                Spacer(modifier = Modifier.width(spacing.small))
                Text(text = post.likes.toString(), style = MaterialTheme.typography.h4)
            }
            Spacer(modifier = Modifier.width(spacing.medium))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = onCommentClick) {
                    Icon(
                        imageVector = Icons.Rounded.Message,
                        contentDescription = "Comments"
                    )
                }
                Spacer(modifier = Modifier.width(spacing.small))
                Text(text = "$numComments", style = MaterialTheme.typography.h4)
            }
        }
    }
}