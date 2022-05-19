package ch.proximeety.proximeety.presentation.views.nfc.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import ch.proximeety.proximeety.core.entities.Tag

@Composable
fun SectionHeader(tag: Tag?, modifier: Modifier = Modifier) {
    Column {
        if (tag != null) {
            Text(tag.name, style = MaterialTheme.typography.h1)
            Text(
                "By ${tag.owner.displayName}",
                style = MaterialTheme.typography.h1,
                fontWeight = FontWeight.Light,
                color = Color.Gray
            )
        }
    }
}