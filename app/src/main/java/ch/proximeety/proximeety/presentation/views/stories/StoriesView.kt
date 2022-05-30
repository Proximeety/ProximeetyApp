package ch.proximeety.proximeety.presentation.views.stories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ch.proximeety.proximeety.presentation.theme.spacing
import ch.proximeety.proximeety.presentation.views.stories.components.ButtonExtended
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


/**
 * The Stories View.
 */
@OptIn(ExperimentalCoilApi::class)
@Composable
fun StoriesView(
    viewModel: StoriesViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val user = viewModel.user.value.observeAsState()
    val currentStory = viewModel.currentStory.value
    val nextStory = viewModel.nextStory.value
    val currentStoryIndex = viewModel.currentStoryIndex.value
    val currentUserStoryCount = viewModel.storyCount.value
    val isAuthenticatedUserProfile = viewModel.isAuthenticatedUserProfile

    val progress = viewModel.progress.value

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (currentStory?.storyURL != null) {
            Image(
                painter = rememberImagePainter(currentStory.storyURL),
                contentScale = ContentScale.Crop,
                contentDescription = "Story of ${currentStory.userDisplayName}",
                modifier = Modifier.fillMaxSize()
            )
        } else {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        Box(modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .background(Color.Transparent)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { viewModel.onEvent(StoriesEvent.PreviousStory) })
                Box(modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .background(Color.Transparent)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { viewModel.onEvent(StoriesEvent.NextStory) })
            }
            Column(horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Top) {
                Row(modifier = Modifier.padding(spacing.small)) {
                    for (i in (0 until currentUserStoryCount)) {
                        LinearProgressIndicator(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(2.dp)),
                            progress = if (i == currentStoryIndex) progress else if (i < currentStoryIndex) 1f else 0f
                        )
                        if (i != currentUserStoryCount - 1) {
                            Spacer(modifier = Modifier.width(spacing.extraSmall))
                        }
                    }
                }
                Row(
                    modifier = Modifier.padding(
                        vertical = spacing.extraSmall,
                        horizontal = spacing.small
                    ).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {
                        Spacer(modifier = Modifier.width(spacing.small))
                        Image(
                            painter = rememberImagePainter(user.value?.profilePicture),
                            contentDescription = "Profile picture of ${user.value?.displayName}",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .aspectRatio(1f)
                                .background(Color.Gray)
                                .clickable { viewModel.onEvent(StoriesEvent.OnClickOnUserPicture) }
                        )
                        Spacer(modifier = Modifier.width(spacing.small))
                        Column {
                            Text(
                                text = user.value?.displayName.toString(),
                                style = MaterialTheme.typography.h4
                            )
                            val time =  LocalDateTime.ofInstant(
                                Instant.ofEpochMilli(currentStory?.timestamp ?: 0),
                                TimeZone.getDefault().toZoneId());
                            val formatter = DateTimeFormatter.ofPattern("MM/dd HH:mm")
                            var formatted = time.format(formatter)
                            if (formatted == "01/01 01:00") {
                                formatted = "No Story available" // when the currentStory == null
                            }
                            Text(text = formatted, style = MaterialTheme.typography.h5, color = Color.Gray)
                        }
                    }
                    if (isAuthenticatedUserProfile) {
                        ButtonExtended(
                            viewModel = viewModel,
                            onDelete = { viewModel.onEvent(StoriesEvent.DeleteStory(currentStory)) }
                        )
                    }
                }
            }
        }
    }
}
