package ch.proximeety.proximeety.presentation.views.nfc.components

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import ch.proximeety.proximeety.core.entities.Tag
import ch.proximeety.proximeety.presentation.theme.spacing
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.network.HttpException
import coil.request.ImageRequest
import com.google.accompanist.placeholder.material.placeholder

@Composable
fun SectionPictures(tag: Tag?, modifier: Modifier = Modifier, width: Float, height: Float) {
    Row(
        modifier = Modifier
            .height(width.dp * 2f / 3f - spacing.large * 1f / 3f)
            .fillMaxWidth()
    ) {
        Picture("", width.dp * 2f / 3f - spacing.large * 1f / 3f)
        Spacer(modifier = Modifier.width(spacing.large))
        Column {
            Picture("", (height.dp - spacing.large) * 0.5f)
            Spacer(modifier = Modifier.height(spacing.large))
            Picture("", (height.dp - spacing.large) * 0.5f)
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun Picture(request: String, size: Dp) {
    Image(
        painter = rememberImagePainter(request),
        contentDescription = "Image of the place",
        modifier = Modifier
            .aspectRatio(1f)
            .size(size)
            .clip(MaterialTheme.shapes.large)
            .placeholder(true),
        contentScale = ContentScale.Crop
    )

}