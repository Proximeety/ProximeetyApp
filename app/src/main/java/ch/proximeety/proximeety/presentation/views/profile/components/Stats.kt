package ch.proximeety.proximeety.presentation.views.profile.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ch.proximeety.proximeety.presentation.theme.spacing

@Composable
fun Stats(
    list : List<Pair<String, String>>,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        list.forEach {
            Stat(name = it.first, value = it.second)
        }
    }
}

@Composable
private fun Stat(name : String, value : String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = value,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h1,
            fontWeight = FontWeight.Bold,

            )
        Spacer(modifier = Modifier.height(spacing.extraSmall))
        Text(
            text = name,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h3,
            fontWeight = FontWeight.Normal,
            color = Color.Gray
        )
    }
}
