//package de.ljz.questify.ui.ds.components
//
//import androidx.compose.animation.animateColorAsState
//import androidx.compose.animation.core.animateFloatAsState
//import androidx.compose.animation.core.tween
//import androidx.compose.foundation.Canvas
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.shadow
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.Path
//import androidx.compose.ui.graphics.drawscope.Fill
//import androidx.compose.ui.graphics.drawscope.Stroke
//import androidx.compose.ui.graphics.drawscope.rotate
//import androidx.compose.ui.semantics.Role
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import de.ljz.questify.ui.ds.theme.GameTheme
//
///**
// * Game-inspired radio button for Questify
// *
// * This radio button component uses a shield-like design and game-inspired styling.
// * It includes:
// * - Shield-like outer ring
// * - Animated selection indicator
// * - Fantasy-inspired colors
// * - Optional label
// */
//
//@Composable
//fun GameRadioButton(
//    selected: Boolean,
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier,
//    enabled: Boolean = true,
//    label: String? = null
//) {
//    val interactionSource = remember { MutableInteractionSource() }
//
//    // Animate scale for selection indicator
//    val indicatorScale by animateFloatAsState(
//        targetValue = if (selected) 1f else 0f,
//        animationSpec = tween(durationMillis = 200),
//        label = "indicatorScale"
//    )
//
//    // Animate colors
//    val borderColor by animateColorAsState(
//        targetValue = when {
//            !enabled -> MaterialTheme.colorScheme.outline.copy(alpha = 0.38f)
//            selected -> MaterialTheme.colorScheme.primary
//            else -> MaterialTheme.colorScheme.outline
//        },
//        animationSpec = tween(durationMillis = 200),
//        label = "borderColor"
//    )
//
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = modifier.clickable(
//            interactionSource = interactionSource,
//            indication = null,
//            enabled = enabled,
//            role = Role.RadioButton,
//            onClick = onClick
//        )
//    ) {
//        // Shield-like radio button
//        Canvas(modifier = Modifier.size(24.dp)) {
//            // Draw outer shield shape
//            val shieldPath = Path().apply {
//                val width = size.width
//                val height = size.height
//                val cornerRadius = width * 0.2f
//
//                // Top left corner
//                moveTo(0f, cornerRadius)
//                quadraticBezierTo(0f, 0f, cornerRadius, 0f)
//
//                // Top edge
//                lineTo(width - cornerRadius, 0f)
//
//                // Top right corner
//                quadraticBezierTo(width, 0f, width, cornerRadius)
//
//                // Right edge with slight inward curve
//                lineTo(width, height * 0.7f)
//                quadraticBezierTo(
//                    width, height,
//                    width * 0.5f, height
//                )
//
//                // Left edge with slight inward curve
//                quadraticBezierTo(
//                    0f, height,
//                    0f, height * 0.7f
//                )
//
//                close()
//            }
//
//            // Draw background
//            drawPath(
//                path = shieldPath,
//                color = MaterialTheme.colorScheme.surfaceContainer,
//                style = Fill
//            )
//
//            // Draw border
//            drawPath(
//                path = shieldPath,
//                color = borderColor,
//                style = Stroke(width = 2.dp.toPx())
//            )
//
//            // Draw selection indicator
//            if (indicatorScale > 0) {
//                val centerX = size.width / 2
//                val centerY = size.height / 2
//                val radius = size.width * 0.25f * indicatorScale
//
//                drawCircle(
//                    color = MaterialTheme.colorScheme.primary,
//                    radius = radius,
//                    center = Offset(centerX, centerY)
//                )
//            }
//        }
//
//        // Optional label
//        if (label != null) {
//            Spacer(modifier = Modifier.width(8.dp))
//            Text(
//                text = label,
//                style = MaterialTheme.typography.bodyMedium,
//                color = if (enabled) {
//                    MaterialTheme.colorScheme.onSurface
//                } else {
//                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
//                }
//            )
//        }
//    }
//}
//
//// Alternative stylized radio button with a gem-like design
//@Composable
//fun GameGemRadioButton(
//    selected: Boolean,
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier,
//    enabled: Boolean = true,
//    label: String? = null
//) {
//    val interactionSource = remember { MutableInteractionSource() }
//
//    // Animate rotation for gem
//    val gemRotation by animateFloatAsState(
//        targetValue = if (selected) 45f else 0f,
//        animationSpec = tween(durationMillis = 300),
//        label = "gemRotation"
//    )
//
//    // Animate colors
//    val gemColor by animateColorAsState(
//        targetValue = when {
//            !enabled -> MaterialTheme.colorScheme.outline.copy(alpha = 0.38f)
//            selected -> MaterialTheme.colorScheme.primary
//            else -> MaterialTheme.colorScheme.outline
//        },
//        animationSpec = tween(durationMillis = 200),
//        label = "gemColor"
//    )
//
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = modifier.clickable(
//            interactionSource = interactionSource,
//            indication = null,
//            enabled = enabled,
//            role = Role.RadioButton,
//            onClick = onClick
//        )
//    ) {
//        // Gem-like radio button
//        Canvas(modifier = Modifier.size(24.dp)) {
//            rotate(degrees = gemRotation) {
//                // Draw gem shape (diamond)
//                val centerX = size.width / 2
//                val centerY = size.height / 2
//                val gemSize = size.width * 0.6f
//
//                val gemPath = Path().apply {
//                    moveTo(centerX, centerY - gemSize / 2) // Top
//                    lineTo(centerX + gemSize / 2, centerY) // Right
//                    lineTo(centerX, centerY + gemSize / 2) // Bottom
//                    lineTo(centerX - gemSize / 2, centerY) // Left
//                    close()
//                }
//
//                // Draw gem background
//                drawPath(
//                    path = gemPath,
//                    color = if (selected) {
//                        MaterialTheme.colorScheme.primaryContainer
//                    } else {
//                        MaterialTheme.colorScheme.surfaceContainer
//                    },
//                    style = Fill
//                )
//
//                // Draw gem border
//                drawPath(
//                    path = gemPath,
//                    color = gemColor,
//                    style = Stroke(width = 2.dp.toPx())
//                )
//
//                // Draw gem facets if selected
//                if (selected) {
//                    // Top facet
//                    drawLine(
//                        color = gemColor,
//                        start = Offset(centerX, centerY - gemSize / 2),
//                        end = Offset(centerX, centerY),
//                        strokeWidth = 1.dp.toPx()
//                    )
//
//                    // Right facet
//                    drawLine(
//                        color = gemColor,
//                        start = Offset(centerX + gemSize / 2, centerY),
//                        end = Offset(centerX, centerY),
//                        strokeWidth = 1.dp.toPx()
//                    )
//
//                    // Bottom facet
//                    drawLine(
//                        color = gemColor,
//                        start = Offset(centerX, centerY + gemSize / 2),
//                        end = Offset(centerX, centerY),
//                        strokeWidth = 1.dp.toPx()
//                    )
//
//                    // Left facet
//                    drawLine(
//                        color = gemColor,
//                        start = Offset(centerX - gemSize / 2, centerY),
//                        end = Offset(centerX, centerY),
//                        strokeWidth = 1.dp.toPx()
//                    )
//                }
//            }
//        }
//
//        // Optional label
//        if (label != null) {
//            Spacer(modifier = Modifier.width(8.dp))
//            Text(
//                text = label,
//                style = MaterialTheme.typography.bodyMedium,
//                color = if (enabled) {
//                    MaterialTheme.colorScheme.onSurface
//                } else {
//                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
//                }
//            )
//        }
//    }
//}
//
//@Composable
//fun GameRadioGroup(
//    options: List<String>,
//    selectedOption: String,
//    onOptionSelected: (String) -> Unit,
//    modifier: Modifier = Modifier,
//    enabled: Boolean = true,
//    useGemStyle: Boolean = false
//) {
//    Column(modifier = modifier) {
//        options.forEach { option ->
//            val isSelected = option == selectedOption
//            if (useGemStyle) {
//                GameGemRadioButton(
//                    selected = isSelected,
//                    onClick = { onOptionSelected(option) },
//                    enabled = enabled,
//                    label = option,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 4.dp)
//                )
//            } else {
//                GameRadioButton(
//                    selected = isSelected,
//                    onClick = { onOptionSelected(option) },
//                    enabled = enabled,
//                    label = option,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 4.dp)
//                )
//            }
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GameRadioButtonPreview() {
//    GameTheme {
//        Surface(color = MaterialTheme.colorScheme.background) {
//            Column(modifier = Modifier.padding(16.dp)) {
//                var selectedOption by remember { mutableStateOf("Easy") }
//
//                Text(
//                    text = "Quest Difficulty",
//                    style = MaterialTheme.typography.titleMedium,
//                    modifier = Modifier.padding(bottom = 8.dp)
//                )
//
//                GameRadioGroup(
//                    options = listOf("Easy", "Medium", "Hard", "Epic"),
//                    selectedOption = selectedOption,
//                    onOptionSelected = { selectedOption = it }
//                )
//
//                Spacer(modifier = Modifier.height(32.dp))
//
//                Text(
//                    text = "Quest Type (Gem Style)",
//                    style = MaterialTheme.typography.titleMedium,
//                    modifier = Modifier.padding(bottom = 8.dp)
//                )
//
//                var selectedGemOption by remember { mutableStateOf("Main Quest") }
//
//                GameRadioGroup(
//                    options = listOf("Main Quest", "Side Quest", "Daily", "Habit"),
//                    selectedOption = selectedGemOption,
//                    onOptionSelected = { selectedGemOption = it },
//                    useGemStyle = true
//                )
//            }
//        }
//    }
//}