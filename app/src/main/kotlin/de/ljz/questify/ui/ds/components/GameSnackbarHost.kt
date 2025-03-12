//package de.ljz.questify.ui.ds.components
//
//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.animation.core.tween
//import androidx.compose.animation.fadeIn
//import androidx.compose.animation.fadeOut
//import androidx.compose.animation.slideInVertically
//import androidx.compose.animation.slideOutVertically
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Snackbar
//import androidx.compose.material3.SnackbarData
//import androidx.compose.material3.SnackbarHost
//import androidx.compose.material3.SnackbarHostState
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.shadow
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.Shape
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import de.ljz.questify.ui.ds.theme.GameTheme
//import de.ljz.questify.ui.ds.theme.scroll
//import kotlinx.coroutines.delay
//
///**
// * Game-inspired snackbar host for Questify
// *
// * This component provides a game-themed alternative to Material 3's SnackbarHost.
// *
// * Features:
// * - Scroll shape by default (can be customized)
// * - Fantasy-inspired colors
// * - Animated entry and exit
// * - Support for action buttons
// */
//
//@Composable
//fun GameSnackbarHost(
//    hostState: SnackbarHostState,
//    modifier: Modifier = Modifier,
//    shape: Shape = MaterialTheme.shapes.scroll,
//    containerColor: Color = MaterialTheme.colorScheme.surfaceContainer,
//    contentColor: Color = MaterialTheme.colorScheme.onSurface,
//    actionColor: Color = MaterialTheme.colorScheme.primary,
//    snackbar: @Composable (SnackbarData) -> Unit = { snackbarData ->
//        GameSnackbar(
//            snackbarData = snackbarData,
//            shape = shape,
//            containerColor = containerColor,
//            contentColor = contentColor,
//            actionColor = actionColor
//        )
//    }
//) {
//    SnackbarHost(
//        hostState = hostState,
//        modifier = modifier,
//        snackbar = snackbar
//    )
//}
//
//@Composable
//fun GameSnackbar(
//    snackbarData: SnackbarData,
//    modifier: Modifier = Modifier,
//    shape: Shape = MaterialTheme.shapes.scroll,
//    containerColor: Color = MaterialTheme.colorScheme.surfaceContainer,
//    contentColor: Color = MaterialTheme.colorScheme.onSurface,
//    actionColor: Color = MaterialTheme.colorScheme.primary,
//    borderColor: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
//) {
//    Box(
//        modifier = modifier
//            .shadow(4.dp, shape)
//            .clip(shape)
//            .background(
//                color = containerColor,
//                shape = shape
//            )
//            .border(
//                width = 1.dp,
//                color = borderColor,
//                shape = shape
//            )
//            .padding(16.dp)
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text(
//                text = snackbarData.visuals.message,
//                style = MaterialTheme.typography.bodyMedium,
//                color = contentColor,
//                modifier = Modifier.weight(1f)
//            )
//
//            snackbarData.visuals.actionLabel?.let { actionLabel ->
//                Spacer(modifier = Modifier.width(16.dp))
//
//                GameButton(
//                    onClick = { snackbarData.performAction() },
//                    text = actionLabel,
//                    variant = GameButtonVariant.TERTIARY
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun AnimatedGameSnackbarHost(
//    hostState: SnackbarHostState,
//    modifier: Modifier = Modifier,
//    shape: Shape = MaterialTheme.shapes.scroll,
//    containerColor: Color = MaterialTheme.colorScheme.surfaceContainer,
//    contentColor: Color = MaterialTheme.colorScheme.onSurface,
//    actionColor: Color = MaterialTheme.colorScheme.primary
//) {
//    Box(
//        modifier = modifier,
//        contentAlignment = Alignment.BottomCenter
//    ) {
//        AnimatedVisibility(
//            visible = hostState.currentSnackbarData != null,
//            enter = slideInVertically(
//                initialOffsetY = { it },
//                animationSpec = tween(durationMillis = 300)
//            ) + fadeIn(animationSpec = tween(durationMillis = 300)),
//            exit = slideOutVertically(
//                targetOffsetY = { it },
//                animationSpec = tween(durationMillis = 300)
//            ) + fadeOut(animationSpec = tween(durationMillis = 300))
//        ) {
//            hostState.currentSnackbarData?.let { snackbarData ->
//                GameSnackbar(
//                    snackbarData = snackbarData,
//                    shape = shape,
//                    containerColor = containerColor,
//                    contentColor = contentColor,
//                    actionColor = actionColor,
//                    modifier = Modifier.padding(16.dp)
//                )
//            }
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GameSnackbarPreview() {
//    GameTheme {
//        Surface(color = MaterialTheme.colorScheme.background) {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(16.dp),
//                contentAlignment = Alignment.BottomCenter
//            ) {
//                // Simple snackbar
//                GameSnackbar(
//                    snackbarData = object : SnackbarData {
//                        override val visuals: SnackbarVisuals = object : SnackbarVisuals {
//                            override val actionLabel: String? = "Undo"
//                            override val duration: SnackbarDuration = SnackbarDuration.Short
//                            override val message: String = "Quest completed successfully!"
//                            override val withDismissAction: Boolean = false
//                        }
//
//                        override fun dismiss() {}
//                        override fun performAction() {}
//                    }
//                )
//            }
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun AnimatedGameSnackbarPreview() {
//    GameTheme {
//        Surface(color = MaterialTheme.colorScheme.background) {
//            val snackbarHostState = remember { SnackbarHostState() }
//            var showSnackbar by remember { mutableStateOf(false) }
//
//            LaunchedEffect(showSnackbar) {
//                if (showSnackbar) {
//                    snackbarHostState.showSnackbar(
//                        message = "New quest added to your journal!",
//                        actionLabel = "View"
//                    )
//                    delay(3000)
//                    showSnackbar = false
//                } else {
//                    delay(1000)
//                    showSnackbar = true
//                }
//            }
//
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.BottomCenter
//            ) {
//                AnimatedGameSnackbarHost(
//                    hostState = snackbarHostState
//                )
//            }
//        }
//    }
//}
//
//// Helper classes for preview
//private enum class SnackbarDuration { Short, Long, Indefinite }
//
//private interface SnackbarVisuals {
//    val message: String
//    val actionLabel: String?
//    val withDismissAction: Boolean
//    val duration: SnackbarDuration
//}