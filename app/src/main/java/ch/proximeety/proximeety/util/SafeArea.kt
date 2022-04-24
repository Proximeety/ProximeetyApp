package ch.proximeety.proximeety.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun SafeArea(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .then(modifier),
        content = content
    )
}
