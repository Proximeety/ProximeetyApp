package ch.proximeety.proximeety.presentation.views.profile.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FriendStat(
    count: Int
) {
    val padding = 7.dp
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(CircleShape)
            .border(width = 1.dp, color = Color.Black, shape = CircleShape)
            .size(100.dp)
    ) {
        Text(
            text = count.toString(),
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            modifier = Modifier.padding(top = padding, start = padding, end = padding)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Friends",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = padding, start = padding, end = padding)
        )
    }
}