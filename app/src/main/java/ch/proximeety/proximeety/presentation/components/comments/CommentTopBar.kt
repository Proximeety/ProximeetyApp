package ch.proximeety.proximeety.presentation.components.comments

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CommentTopBar(numComments: Int, onCloseClick: () -> Unit) {
    val title = "Comments"
    val contentDescription = "Close Comment Section"

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
            Text(
                text = "$numComments",
                style = MaterialTheme.typography.body1
            )
        }

        IconButton(onClick = onCloseClick) {
            Icon(Icons.Outlined.Close, contentDescription = contentDescription)
        }
    }
}