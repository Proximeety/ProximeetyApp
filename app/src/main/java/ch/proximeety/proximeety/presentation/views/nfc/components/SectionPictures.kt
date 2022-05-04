package ch.proximeety.proximeety.presentation.views.nfc.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ch.proximeety.proximeety.core.entities.Tag
import ch.proximeety.proximeety.presentation.theme.spacing
import com.google.accompanist.placeholder.material.placeholder

@Composable
fun SectionPictures(tag: Tag?, modifier: Modifier = Modifier, width: Float, height: Float) {
    Row(
        modifier = Modifier
            .height(width.dp * 2f / 3f - MaterialTheme.spacing.large * 1f / 3f)
            .fillMaxWidth()
    ) {
        Picture(width.dp * 2f / 3f - MaterialTheme.spacing.large * 1f / 3f)
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.large))
        Column {
            Picture((height.dp - MaterialTheme.spacing.large) * 0.5f)
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
            Picture((height.dp - MaterialTheme.spacing.large) * 0.5f)
        }
    }
}

@Composable
private fun Picture(size: Dp) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .size(size)
            .clip(MaterialTheme.shapes.large)
            .placeholder(true)
    )
}