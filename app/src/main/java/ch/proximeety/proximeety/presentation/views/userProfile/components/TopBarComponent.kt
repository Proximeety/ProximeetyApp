package ch.proximeety.proximeety.presentation.views.userProfile.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
fun ProfileTopBar() {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = Color.LightGray
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .height(50.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                modifier = Modifier
                    .fillMaxHeight(),
                onClick = {/* TODO */ })
            {
                Text(
                    text = "Back",
                    color = Color.Black
                )
            }

            Text(
                text = "Proximeety", modifier = Modifier.padding(all = 5.dp),
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center
            )

            TextButton(
                modifier = Modifier
                    .fillMaxHeight(),
                onClick = {/* TODO */ })
            {
                Text(
                    text = "Settings",
                    color = Color.Black
                )
            }
        }
    }
}