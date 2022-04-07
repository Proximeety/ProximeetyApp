package ch.proximeety.proximeety.presentation.views.upload.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ch.proximeety.proximeety.presentation.theme.spacing

@Composable
fun PostPreviewButtonBar(
    onCancel: () -> Unit,
    onValidate: () -> Unit,
    modifier: Modifier = Modifier,
    postTypeName: String,
) {
    Row(
        modifier = Modifier
            .padding(bottom = MaterialTheme.spacing.large)
            .then(modifier)
    ) {
        IconButton(
            onClick = onCancel,
        ) {
            Icon(
                imageVector = Icons.Default.Cancel,
                contentDescription = "Cancel $postTypeName",
                modifier = Modifier.size(48.dp)
            )
        }
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
        IconButton(
            onClick = onValidate,
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Upload $postTypeName",
                modifier = Modifier.size(48.dp)
            )
        }
    }
}
