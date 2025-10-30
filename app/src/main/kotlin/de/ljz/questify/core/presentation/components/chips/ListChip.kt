package de.ljz.questify.core.presentation.components.chips

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ListChip(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null,
    label: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    interactionSource: MutableInteractionSource? = null
) {
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }

    Surface(
        color = when {
            selected -> MaterialTheme.colorScheme.secondaryContainer
            else -> Color.Transparent
        },
        contentColor = when {
            selected -> MaterialTheme.colorScheme.onSecondaryContainer
            else -> MaterialTheme.colorScheme.onSurfaceVariant
        },
        border = BorderStroke(
            width = 1.dp,
            color = when {
                selected -> MaterialTheme.colorScheme.secondaryContainer
                else -> MaterialTheme.colorScheme.outlineVariant
            },
        ),
        shape = CircleShape,
        modifier = modifier
            .clip(CircleShape)
            .height(32.dp)
            .combinedClickable(
                interactionSource = interactionSource,
                onClick = onClick,
                onLongClick = onLongClick
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(
                    start = 12.dp,
                    end = 12.dp
                ),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.let {
                CompositionLocalProvider(
                    LocalContentColor provides MaterialTheme.colorScheme.primary
                ) {
                    leadingIcon()
                }
            }

            CompositionLocalProvider(
                value = LocalTextStyle provides TextStyle(
                    fontWeight = FontWeight(500),
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            ) {
                label()
            }
        }
    }
}