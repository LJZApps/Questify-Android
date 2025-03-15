package de.ljz.questify.ui.features.first_setup

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import de.ljz.questify.ui.features.first_setup.sub_pages.IntroductionPage
import de.ljz.questify.ui.features.first_setup.sub_pages.QuickSettingPage
import de.ljz.questify.ui.features.first_setup.sub_pages.UserSetupPage
import de.ljz.questify.ui.features.main.navigation.MainRoute
import de.ljz.questify.util.NavBarConfig
import kotlinx.coroutines.launch

@Composable
fun FirstSetupScreen(
    viewModel: FirstSetupViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    val userSetupPageUiState = uiState.userSetupPageUiState

    val pagerState = rememberPagerState(pageCount = { 3 })
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.updateImageUri(uri)
    }

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
                    userScrollEnabled = true
                ) { currentPage ->
                    when (currentPage) {
                        0 -> IntroductionPage(
                            visible = visible
                        )

                        1 -> UserSetupPage(
                            displayName = userSetupPageUiState.displayName,
                            aboutMe = userSetupPageUiState.aboutMe,
                            imageUri = userSetupPageUiState.imageUri,
                            onDisplayNameChange = {
                                viewModel.updateDisplayName(it)
                            },
                            onAboutMeChange = {
                                viewModel.updateAboutMe(it)
                            },
                            requestImagePicker = {
                                imagePickerLauncher.launch("image/*")
                            }
                        )

                        2 -> {
                            QuickSettingPage()
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
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)

                        if (pagerState.currentPage < pagerState.pageCount - 1) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        } else {
                            if (userSetupPageUiState.pickedProfilePicture) {
                                val profilePicture = userSetupPageUiState.imageUri?.let {
                                    viewModel.saveImageToInternalStorage(
                                        context = context,
                                        uri = it
                                    )
                                }
                                viewModel.setSetupDone(profilePicture ?: "")
                            } else if (userSetupPageUiState.imageUri != null) {
                                viewModel.setSetupDone(userSetupPageUiState.imageUri.toString())
                            } else {
                                viewModel.setSetupDone("")
                            }

                            navController.navigate(MainRoute) {
                                popUpTo<MainRoute>{
                                    inclusive = true
                                }
                            }
                        }
                    }
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Crossfade(
                    targetState = pagerState.currentPage == pagerState.pageCount - 1,
                ) {
                    if (it) {
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
        }
    )
}