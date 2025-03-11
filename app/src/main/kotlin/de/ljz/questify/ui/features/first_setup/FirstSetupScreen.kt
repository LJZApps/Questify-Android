package de.ljz.questify.ui.features.first_setup

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
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
    val uiState by viewModel.uiState.collectAsState()
    val userSetupPageUiState = uiState.userSetupPageUiState

    val pagerState = rememberPagerState(pageCount = { 2 })
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
        NavBarConfig.transparentNavBar = true
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.updateImageUri(uri)
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

    // Berechne Animation für den FAB
    val fabScale by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
    )

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    scope.launch {
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
                modifier = Modifier
                    .scale(fabScale)
                    .shadow(16.dp, CircleShape),
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            ) {
                if (pagerState.currentPage == pagerState.pageCount - 1) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Complete setup",
                        tint = Color.White
                    )
                } else {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Next screen",
                        tint = Color.White
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.background.copy(alpha = 0.95f)
                        )
                    )
                )
        ) {
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
                    }
                }

                // PageIndicator - modernisiert
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    repeat(pagerState.pageCount) { index ->
                        val isSelected = pagerState.currentPage == index
                        val indicatorSize = if (isSelected) 14.dp else 10.dp

                        Box(
                            modifier = Modifier
                                .size(indicatorSize)
                                .shadow(4.dp, CircleShape)
                                .clip(CircleShape)
                                .background(
                                    if (isSelected)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                                )
                                .graphicsLayer {
                                    scaleX = if (isSelected) 1f else 0.8f
                                    scaleY = if (isSelected) 1f else 0.8f
                                }
                        )
                    }
                }
            }
        }
    }
}