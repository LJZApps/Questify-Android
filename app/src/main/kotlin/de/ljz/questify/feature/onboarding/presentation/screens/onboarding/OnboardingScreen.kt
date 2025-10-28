package de.ljz.questify.feature.onboarding.presentation.screens.onboarding

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.ljz.questify.feature.onboarding.presentation.screens.onboarding.pages.StartQuestifyPage
import de.ljz.questify.feature.onboarding.presentation.screens.onboarding.pages.TutorialQuestsPage
import de.ljz.questify.feature.onboarding.presentation.screens.onboarding.pages.TutorialRewardsPage
import de.ljz.questify.feature.onboarding.presentation.screens.onboarding.pages.WelcomePage
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
    onNavigateToMainScreen: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    OnboardingScreen(
        uiState = uiState,
        onUiEvent = { event ->
            when (event) {
                is OnboardingUiEvent.OnOnboardingFinished -> {
                    onNavigateToMainScreen()

                    viewModel.onUiEvent(event)
                }

                else -> viewModel.onUiEvent(event)
            }
        }
    )
}

@Composable
private fun OnboardingScreen(
    uiState: OnboardingUiState,
    onUiEvent: (OnboardingUiEvent) -> Unit
) {
    val pagerState = rememberPagerState { 4 }
    val scope = rememberCoroutineScope()

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
            ) {
                if ((pagerState.pageCount - 1) != pagerState.currentPage) {
                    Surface(
                        shadowElevation = 4.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .navigationBarsPadding(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            HorizontalDivider(modifier = Modifier.fillMaxWidth())

                            Row(
                                Modifier
                                    .wrapContentHeight()
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                repeat(pagerState.pageCount) { iteration ->
                                    val color =
                                        if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                                    Box(
                                        modifier = Modifier
                                            .padding(2.dp)
                                            .clip(CircleShape)
                                            .background(color)
                                            .size(8.dp)
                                    )
                                }
                            }

                            Button(
                                onClick = {
                                    scope.launch {
                                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp)
                            ) {
                                Text(text = "Weiter")
                            }
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.25f),
                            MaterialTheme.colorScheme.surface
                        )
                    )
                ),
            userScrollEnabled = false
        ) { page ->
            when (page) {
                0 -> WelcomePage()
                1 -> TutorialQuestsPage()
                2 -> TutorialRewardsPage()
                3 -> StartQuestifyPage()
            }
        }
    }
}

