package ch.proximeety.proximeety.presentation.views.messagesScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import coil.compose.rememberImagePainter

@Composable
fun MessagesProfilePic(
    picUrl: String?, displayName: String?, size: Dp
) {
    Image(
        painter = rememberImagePainter(picUrl),
        contentDescription = "Profile picture of $displayName",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(size)
    )
}