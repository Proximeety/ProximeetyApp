package ch.proximeety.proximeety.presentation.views.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.presentation.theme.spacing
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder

@OptIn(ExperimentalCoilApi::class)
@Composable
fun Stories(users: List<User>, onStoryClick: (String) -> Unit, loading: Boolean = false) {
    LazyRow(
        modifier = Modifier
            .padding(spacing.medium)
            .fillMaxWidth()
            .height(64.dp)
    ) {
        item {
        }
        items(users) {
            Element(loading = loading) {
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { onStoryClick(it.id) },
                    painter = rememberImagePainter(it.profilePicture),
                    contentDescription = "Profile picture of ${it.displayName}",
                )
            }
            Spacer(modifier = Modifier.width(spacing.medium))
        }
    }
}

@Composable
private fun Element(
    loading: Boolean,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(
                Color.Gray
            )
            .placeholder(
                visible = loading,
                highlight = PlaceholderHighlight.fade()
            ),
        content = content
    )
}