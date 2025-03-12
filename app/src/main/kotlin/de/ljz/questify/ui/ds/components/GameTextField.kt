package de.ljz.questify.ui.ds.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.ljz.questify.ui.ds.theme.GameTheme
import de.ljz.questify.ui.ds.theme.scrollShape

/**
 * Game-inspired text field for Questify
 * 
 * This text field component uses a scroll-like shape and game-inspired styling.
 * It includes:
 * - Scroll shape by default (can be customized)
 * - Animated focus effects
 * - Fantasy-inspired colors
 * - Support for leading and trailing icons
 * - Error state display
 */

@Composable
fun GameTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorMessage: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    shape: Shape = MaterialTheme.shapes.scrollShape(4f),
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    
    // Animate border color and width based on focus state
    val borderColor by animateColorAsState(
        targetValue = when {
            isError -> MaterialTheme.colorScheme.error
            isFocused -> MaterialTheme.colorScheme.primary
            else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
        },
        animationSpec = tween(durationMillis = 150),
        label = "borderColor"
    )
    
    val borderWidth by animateDpAsState(
        targetValue = if (isFocused) 2.dp else 1.dp,
        animationSpec = tween(durationMillis = 150),
        label = "borderWidth"
    )
    
    // Determine text color based on state
    val textColor = when {
        !enabled -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        isError -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.onSurface
    }
    
    Column(modifier = modifier) {
        // Optional label
        if (label != null) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = when {
                    isError -> MaterialTheme.colorScheme.error
                    isFocused -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.onSurfaceVariant
                },
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
        
        // Main text field
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(2.dp, shape)
                .clip(shape)
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = shape
                )
                .border(
                    width = borderWidth,
                    color = borderColor,
                    shape = shape
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(contentPadding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Leading icon
                if (leadingIcon != null) {
                    Box(modifier = Modifier.padding(end = 8.dp)) {
                        leadingIcon()
                    }
                }
                
                // Text field
                Box(modifier = Modifier.weight(1f)) {
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = enabled,
                        readOnly = readOnly,
                        textStyle = textStyle.copy(color = textColor),
                        keyboardOptions = keyboardOptions,
                        keyboardActions = keyboardActions,
                        singleLine = singleLine,
                        maxLines = maxLines,
                        visualTransformation = visualTransformation,
                        interactionSource = interactionSource,
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
                    )
                    
                    // Placeholder
                    if (value.isEmpty() && placeholder != null) {
                        Text(
                            text = placeholder,
                            style = textStyle,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                        )
                    }
                }
                
                // Trailing icon
                if (trailingIcon != null) {
                    Box(modifier = Modifier.padding(start = 8.dp)) {
                        trailingIcon()
                    }
                }
            }
        }
        
        // Error message
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 4.dp, start = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameTextFieldPreview() {
    GameTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                GameTextField(
                    value = "",
                    onValueChange = {},
                    label = "Quest Title",
                    placeholder = "Enter a title for your quest"
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                GameTextField(
                    value = "Defeat the Dragon",
                    onValueChange = {},
                    label = "Quest Title"
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                GameTextField(
                    value = "Invalid input",
                    onValueChange = {},
                    label = "Quest Title",
                    isError = true,
                    errorMessage = "Title cannot be empty"
                )
            }
        }
    }
}