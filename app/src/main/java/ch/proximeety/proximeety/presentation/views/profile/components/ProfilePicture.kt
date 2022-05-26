package ch.proximeety.proximeety.presentation.views.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
fun ProfilePicture(
    url: String?, displayName: String?, onStoryClick: () -> Unit
) {
    Image(
        painter = rememberImagePainter(url),
        contentDescription = "Profile picture of $displayName",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .aspectRatio(1f, matchHeightConstraintsFirst = false)
            .background(Color.Gray)
            .clickable(onClick = onStoryClick)
    )
}
