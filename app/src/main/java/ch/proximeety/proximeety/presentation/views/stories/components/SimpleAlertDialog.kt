package ch.proximeety.proximeety.presentation.views.stories.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

@Composable
fun SimpleAlertDialog(
    show: State<Boolean>,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (show.value) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = onConfirm)
                { Text(text = "Confirm") }
            },
            dismissButton = {
                TextButton(onClick = onDismiss)
                { Text(text = "Cancel") }
            },
            title = { Text(text = "Delete post") },
            text = { Text(text = "Do you want to continue?") }
        )
    }
}
