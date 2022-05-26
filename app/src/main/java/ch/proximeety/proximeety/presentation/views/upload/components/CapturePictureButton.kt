package ch.proximeety.proximeety.presentation.views.upload.components

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import ch.proximeety.proximeety.R
import ch.proximeety.proximeety.presentation.theme.spacing

@Composable
fun CapturePictureButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val color = if (isPressed) MaterialTheme.colors.primary else Color.White

    Box(modifier = modifier) {
        Button(
            modifier = modifier
                .aspectRatio(1f)
                .fillMaxSize()
                .border(spacing.extraSmall, color, CircleShape)
                .padding(spacing.small)
                .testTag(stringResource(id = R.string.TT_UV_take_picture_button)),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(backgroundColor = color),
            interactionSource = interactionSource,
            onClick = onClick
        ) { }
    }
}