package de.ljz.questify.ui.features.first_setup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.NavigateBefore
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import de.ljz.questify.util.NavBarConfig
import kotlinx.coroutines.launch

@Composable
fun FirstSetupScreen(
    viewModel: FirstSetupViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState = viewModel.uiState.collectAsState().value
    val pagerState = rememberPagerState(pageCount = { 3 })
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        NavBarConfig.transparentNavBar = false
    }

    Scaffold(
        content = { innerPadding ->
            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) { currentPage ->
                when (currentPage) {
                    0 -> {

                    }

                    1 -> {

                    }

                    2 -> {

                    }
                }
            }
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        onClick = {
                            scope.launch {
                                pagerState.scrollToPage(pagerState.currentPage - 1)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                    Button(
                        onClick = {
                            scope.launch {
                                pagerState.scrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    )
}