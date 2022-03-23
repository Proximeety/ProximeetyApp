package ch.proximeety.proximeety.presentation.views.settings.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * The Settings top bar
 */
@Preview
@Composable
fun HomeTopBar() {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = Color.LightGray
    ) {
        TextButton(
            modifier = Modifier
                .fillMaxHeight(),
            onClick = {/* TODO */ })
        {
            Text(
                text = "Back",
                color = Color.Black)
        }
        Text(
            text = "Settings", modifier = Modifier.padding(start = 100.dp),
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center
        )
    }
}