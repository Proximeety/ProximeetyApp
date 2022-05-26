package ch.proximeety.proximeety.presentation.components.comments

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter


@Composable
fun CommentProfilePic(
    picUrl: String?, displayName: String?, small: Boolean = false
) {
    Image(
        painter = rememberImagePainter(picUrl),
        contentDescription = "Profile picture of $displayName",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(if (small) 30.dp else 40.dp)
            .clip(CircleShape)
            .aspectRatio(1f, matchHeightConstraintsFirst = false)
            .background(Color.Gray)
    )
}