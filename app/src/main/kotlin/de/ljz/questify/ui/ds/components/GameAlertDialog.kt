package de.ljz.questify.ui.ds.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import de.ljz.questify.ui.ds.theme.GameTheme
import de.ljz.questify.ui.ds.theme.scroll

/**
 * Game-inspired alert dialog for Questify
 * 
 * This dialog component uses a scroll-like shape and game-inspired styling.
 * It includes:
 * - Scroll shape by default (can be customized)
 * - Fantasy-inspired colors
 * - Support for title, text, and buttons
 * - Customizable content
 */

@Composable
fun GameAlertDialog(
    onDismissRequest: () -> Unit,
    title: String,
    text: String? = null,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.scroll,
    properties: DialogProperties = DialogProperties()
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Box(
            modifier = modifier
                .shadow(8.dp, shape)
                .clip(shape)
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = shape
                )
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    shape = shape
                )
                .padding(24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                
                // Optional text
                if (text != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (dismissButton != null) {
                        Box(modifier = Modifier.weight(1f)) {
                            dismissButton()
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    
                    Box(modifier = Modifier.weight(1f)) {
                        confirmButton()
                    }
                }
            }
        }
    }
}

@Composable
fun GameAlertDialog(
    onDismissRequest: () -> Unit,
    title: String,
    text: String? = null,
    confirmButtonText: String,
    onConfirmClick: () -> Unit,
    dismissButtonText: String? = null,
    onDismissClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.scroll,
    properties: DialogProperties = DialogProperties()
) {
    GameAlertDialog(
        onDismissRequest = onDismissRequest,
        title = title,
        text = text,
        confirmButton = {
            GameButton(
                onClick = onConfirmClick,
                text = confirmButtonText,
                modifier = Modifier.fillMaxWidth()
            )
        },
        dismissButton = dismissButtonText?.let {
            {
                GameButton(
                    onClick = onDismissClick ?: onDismissRequest,
                    text = it,
                    variant = GameButtonVariant.SECONDARY,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        modifier = modifier,
        shape = shape,
        properties = properties
    )
}

@Preview(showBackground = true)
@Composable
fun GameAlertDialogPreview() {
    GameTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            GameAlertDialog(
                onDismissRequest = { },
                title = "Abandon Quest?",
                text = "Are you sure you want to abandon this quest? All progress will be lost.",
                confirmButtonText = "Abandon",
                onConfirmClick = { },
                dismissButtonText = "Cancel",
                onDismissClick = { }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameAlertDialogSingleButtonPreview() {
    GameTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            GameAlertDialog(
                onDismissRequest = { },
                title = "Quest Complete!",
                text = "You have successfully completed this quest and earned 100 XP.",
                confirmButtonText = "Claim Reward",
                onConfirmClick = { }
            )
        }
    }
}