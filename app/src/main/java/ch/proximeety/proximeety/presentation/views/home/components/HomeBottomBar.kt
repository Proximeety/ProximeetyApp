package ch.proximeety.proximeety.presentation.views.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * The HomeView's bottom bar
 */
@Composable
fun homeBottomBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
//        .border(color = Color.Black, width = 1.dp)
//        .background(color = Color.LightGray)
    ) {
        Divider(color = Color.Black)
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .height(50.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Search",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(all = 5.dp)
            )

            Text(
                text = "Camera",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(all = 5.dp)
            )

            Text(
                text = "Profile",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(all = 5.dp)
            )
        }
    }
}