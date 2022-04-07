package ch.proximeety.proximeety.presentation.views.authenticationHome

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ch.proximeety.proximeety.R
import ch.proximeety.proximeety.util.SafeArea
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * The Authentication Home View.
 */
@Composable
fun AuthenticationHomeView(
    viewModel: AuthenticationHomeViewModel = hiltViewModel()
) {
    LaunchedEffect(true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
            }
        }
    }

    SafeArea {
        AuthenticationHomeScreen(viewModel)
    }
}

@Composable
fun AuthenticationHomeScreen(viewModel: AuthenticationHomeViewModel) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val authenticationHomeViewModel : AuthenticationHomeViewModel = viewModel
    val currentPage = authenticationHomeViewModel.currentPage.collectAsState()

    val pagerState = rememberPagerState(
        pageCount = screens.size,
        initialPage = 0,
        infiniteLoop = false
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState
    ) {

        Surface (
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Background
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.background),
                    contentDescription = "background_image",
                    contentScale = ContentScale.FillBounds
                )
                // Page content
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxSize()
                ) {
                    HorizontalPager(state = pagerState) { page ->
                        Column(
                            modifier = Modifier
                                .padding(top = 65.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // First page: Bigger title
                            if (currentPage.value == 0) {
                                Text(
                                    text = screens[page].title,
                                    modifier = Modifier.padding(top = 100.dp, start = 30.dp, end = 30.dp),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 40.sp,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 70.sp
                                )
                            }
                            // Rest of the pages: Smaller description
                            else {
                                Text(
                                    text = screens[page].title,
                                    modifier = Modifier.padding(top = 150.dp, start = 30.dp, end = 30.dp),
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 40.sp
                                )

                                // Last page: Login button
                                if (currentPage.value == pagerState.pageCount - 1) {
                                    Button(
                                        modifier = Modifier.padding(top = 70.dp),
                                        onClick = { viewModel.onEvent(AuthenticationHomeEvent.AuthenticateWithGoogle) }) {
                                        Text(text = "Login")
                                    }
                                }
                            }

                        }
                    }
                    // Page indicator point row
                    PagerIndicator(screens.size, pagerState.currentPage)
                }

                // Bottom bar with 'back' and 'next' buttons
                Box(
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    Row (
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                            .fillMaxWidth(),
                        horizontalArrangement =
                        when (pagerState.currentPage) { 0 -> {
                                Arrangement.End
                            } pagerState.pageCount - 1 -> {
                                Arrangement.Start
                            } else -> {
                                Arrangement.SpaceBetween
                            }
                        }
                    ) {
                        // 'Back' button: Not in the first page
                        if (pagerState.currentPage != 0) {
                            Text(
                                text = "Back",
                                color = Color.White,
                                modifier = Modifier
                                    .clickable {
                                        authenticationHomeViewModel.setCurrentPage(pagerState.currentPage - 1)
                                        scope.launch {
                                            pagerState.animateScrollToPage(
                                                page = currentPage.value
                                            )
                                        }
                                    }
                                    .padding(start = 30.dp),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        // 'Next' button: not in the last page
                        if (pagerState.currentPage != pagerState.pageCount - 1) {
                            Text(
                                text = "Next",
                                color = Color.White,
                                modifier = Modifier
                                    .clickable {
                                        authenticationHomeViewModel.setCurrentPage(pagerState.currentPage + 1)
                                        scope.launch {
                                            pagerState.animateScrollToPage(
                                                page = currentPage.value
                                            )
                                        }
                                    }
                                    .padding(end = 30.dp),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PagerIndicator(size: Int, currentPage: Int) {
    Row(
        modifier = Modifier.padding(bottom = 40.dp)
    ) {
        repeat(size) {
            IndicateIcon(isSelected = it == currentPage)
        }
    }
}

@Composable
fun IndicateIcon(isSelected: Boolean) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .size(10.dp)
            .clip(CircleShape)
            .background(
                if (isSelected) {
                    Color.White
                } else {
                    Color.Gray
                }
            )
    )
}
