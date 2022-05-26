package ch.proximeety.proximeety.presentation.views.nfc.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ch.proximeety.proximeety.core.entities.Tag
import ch.proximeety.proximeety.presentation.theme.spacing
import ch.proximeety.proximeety.presentation.views.nfc.NfcEvent
import ch.proximeety.proximeety.presentation.views.nfc.NfcViewModel
import coil.compose.rememberImagePainter
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun SectionVisitors(tag: Tag?, viewModel: NfcViewModel) {
    Text(
        "Last visitors",
        style = MaterialTheme.typography.h3,
    )
    if (viewModel.canSeeVisitors.value) {

        Column(verticalArrangement = Arrangement.spacedBy(spacing.small)) {
            tag?.visitors?.map { (timestamp, visitor) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.large)
                        .background(Color.Gray.copy(0.1f))
                        .clickable {
                            viewModel.onEvent(
                                NfcEvent.NavigateToUserProfile(
                                    visitor.id
                                )
                            )
                        }
                        .padding(spacing.small),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = rememberImagePainter(visitor.profilePicture),
                        contentDescription = "Profile picture of ${visitor.displayName}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .aspectRatio(1f)
                            .background(Color.Gray)
                    )
                    Spacer(modifier = Modifier.width(spacing.small))
                    Column {
                        Text(
                            text = visitor.displayName,
                            style = MaterialTheme.typography.h4
                        )
                        Text(
                            text = SimpleDateFormat("EEE, MMM d, yyyy", Locale.FRANCE).format(
                                Date(
                                    timestamp
                                )
                            ),
                            fontWeight = FontWeight.Light,
                            style = MaterialTheme.typography.h4,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    } else {
        Text(
            "You are not allowed to see visitors. Please find and scan the tag to see the visitors.",
            style = MaterialTheme.typography.h4,
            color = Color.Gray
        )
    }
}