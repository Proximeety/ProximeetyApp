package ch.proximeety.proximeety.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SafeArea(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .then(modifier),
        content = content
    )
}
