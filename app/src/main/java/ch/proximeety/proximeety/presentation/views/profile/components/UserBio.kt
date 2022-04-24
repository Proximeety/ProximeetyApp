package ch.proximeety.proximeety.presentation.views.profile.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun UserBio(
    modifier: Modifier = Modifier,
    bio: String
) {
    Text(
        text = bio,
        lineHeight = 20.sp,
        fontSize = 18.sp,
        color = Color.Gray,
        modifier = modifier
    )
}