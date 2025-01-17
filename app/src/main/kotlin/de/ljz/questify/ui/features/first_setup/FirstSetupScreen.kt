package de.ljz.questify.ui.features.first_setup

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import de.ljz.questify.ui.features.first_setup.sub_pages.IntroductionPage
import de.ljz.questify.ui.features.first_setup.sub_pages.UserSetupPage
import de.ljz.questify.ui.features.main.navigation.MainRoute
import de.ljz.questify.util.NavBarConfig
import kotlinx.coroutines.launch

@Composable
fun FirstSetupScreen(
    viewModel: FirstSetupViewModel = hiltViewModel(),
    navController: NavController
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        NavBarConfig.transparentNavBar = true
    }

    BackHandler {
        scope.launch {
            if (pagerState.currentPage > 0) {
                pagerState.animateScrollToPage(pagerState.currentPage - 1)
            } else {
                navController.popBackStack()
            }
        }
    }

    Scaffold(
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    userScrollEnabled = false
                ) { currentPage ->
                    when (currentPage) {
                        0 -> IntroductionPage()

                        1 -> UserSetupPage()

                        2 -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "Seite 3")
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(pagerState.pageCount) { index ->
                        val color = if (pagerState.currentPage == index) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                        }

                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .padding(4.dp)
                                .background(
                                    color = color,
                                    shape = MaterialTheme.shapes.small
                                )
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        if (pagerState.currentPage < 2) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        } else {
                            viewModel.setSetupDone()
                            navController.navigate(MainRoute)
                        }
                    }
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                if (pagerState.currentPage == 2) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Next screen"
                    )
                } else {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Next screen"
                    )
                }
            }
        }
    )
}