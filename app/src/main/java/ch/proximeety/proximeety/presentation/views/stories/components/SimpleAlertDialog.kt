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
    val title = "Delete story"
    val question = "Do you want to continue?"
    val confirm = "Confirm"
    val dismiss = "Dismiss"

    if (show.value) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = onConfirm)
                { Text(text = confirm) }
            },
            dismissButton = {
                TextButton(onClick = onDismiss)
                { Text(text = dismiss) }
            },
            title = { Text(text = title) },
            text = { Text(text = question) }
        )
    }
}
