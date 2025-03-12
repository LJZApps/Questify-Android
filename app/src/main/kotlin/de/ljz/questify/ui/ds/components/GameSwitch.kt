//package de.ljz.questify.ui.ds.components
//
//import androidx.compose.animation.animateColorAsState
//import androidx.compose.animation.core.animateDpAsState
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
//import androidx.compose.foundation.layout.offset
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.RoundedCornerShape
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
//import androidx.compose.ui.geometry.Size
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.Path
//import androidx.compose.ui.graphics.StrokeCap
//import androidx.compose.ui.graphics.drawscope.Fill
//import androidx.compose.ui.graphics.drawscope.Stroke
//import androidx.compose.ui.semantics.Role
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import de.ljz.questify.ui.ds.theme.GameTheme
//import de.ljz.questify.ui.ds.theme.shieldShape
//
///**
// * Game-inspired switch for Questify
// *
// * This switch component uses a scroll-like track and shield-like thumb for a game-inspired look.
// * It includes:
// * - Animated thumb movement
// * - Fantasy-inspired colors
// * - Optional label
// * - Alternative torch style
// */
//
//@Composable
//fun GameSwitch(
//    checked: Boolean,
//    onCheckedChange: (Boolean) -> Unit,
//    modifier: Modifier = Modifier,
//    enabled: Boolean = true,
//    label: String? = null
//) {
//    val interactionSource = remember { MutableInteractionSource() }
//
//    // Animate thumb position
//    val thumbOffset by animateDpAsState(
//        targetValue = if (checked) 20.dp else 0.dp,
//        animationSpec = tween(durationMillis = 200),
//        label = "thumbOffset"
//    )
//
//    // Animate colors
//    val trackColor by animateColorAsState(
//        targetValue = if (checked) {
//            MaterialTheme.colorScheme.primaryContainer
//        } else {
//            MaterialTheme.colorScheme.surfaceContainer
//        },
//        animationSpec = tween(durationMillis = 200),
//        label = "trackColor"
//    )
//
//    val thumbColor by animateColorAsState(
//        targetValue = if (checked) {
//            MaterialTheme.colorScheme.primary
//        } else {
//            MaterialTheme.colorScheme.outline
//        },
//        animationSpec = tween(durationMillis = 200),
//        label = "thumbColor"
//    )
//
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = modifier.clickable(
//            interactionSource = interactionSource,
//            indication = null,
//            enabled = enabled,
//            role = Role.Switch,
//            onClick = { onCheckedChange(!checked) }
//        )
//    ) {
//        // Switch track and thumb
//        Box(
//            modifier = Modifier
//                .width(50.dp)
//                .height(30.dp)
//        ) {
//            // Track
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(20.dp)
//                    .align(Alignment.Center)
//                    .clip(RoundedCornerShape(10.dp))
//                    .background(
//                        color = if (enabled) trackColor else MaterialTheme.colorScheme.surfaceVariant,
//                        shape = RoundedCornerShape(10.dp)
//                    )
//                    .border(
//                        width = 1.dp,
//                        color = if (enabled) thumbColor.copy(alpha = 0.5f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.38f),
//                        shape = RoundedCornerShape(10.dp)
//                    )
//            )
//
//            // Thumb
//            Box(
//                modifier = Modifier
//                    .size(24.dp)
//                    .offset(x = thumbOffset)
//                    .align(Alignment.CenterStart)
//                    .shadow(2.dp, MaterialTheme.shapes.shieldShape(4f))
//                    .clip(MaterialTheme.shapes.shieldShape(4f))
//                    .background(
//                        color = if (enabled) thumbColor else MaterialTheme.colorScheme.outline.copy(alpha = 0.38f),
//                        shape = MaterialTheme.shapes.shieldShape(4f)
//                    )
//            )
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
//// Alternative torch-style switch
//@Composable
//fun GameTorchSwitch(
//    checked: Boolean,
//    onCheckedChange: (Boolean) -> Unit,
//    modifier: Modifier = Modifier,
//    enabled: Boolean = true,
//    label: String? = null
//) {
//    val interactionSource = remember { MutableInteractionSource() }
//
//    // Animate flame size
//    val flameScale by animateFloatAsState(
//        targetValue = if (checked) 1f else 0f,
//        animationSpec = tween(durationMillis = 300),
//        label = "flameScale"
//    )
//
//    // Animate colors
//    val torchColor by animateColorAsState(
//        targetValue = if (checked) {
//            MaterialTheme.colorScheme.primary
//        } else {
//            MaterialTheme.colorScheme.outline
//        },
//        animationSpec = tween(durationMillis = 200),
//        label = "torchColor"
//    )
//
//    val flameColor by animateColorAsState(
//        targetValue = if (checked) {
//            Color(0xFFFF9800) // Orange flame
//        } else {
//            Color.Transparent
//        },
//        animationSpec = tween(durationMillis = 200),
//        label = "flameColor"
//    )
//
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = modifier.clickable(
//            interactionSource = interactionSource,
//            indication = null,
//            enabled = enabled,
//            role = Role.Switch,
//            onClick = { onCheckedChange(!checked) }
//        )
//    ) {
//        // Torch switch
//        Canvas(modifier = Modifier.size(40.dp)) {
//            val width = size.width
//            val height = size.height
//
//            // Draw torch handle
//            val handlePath = Path().apply {
//                val handleWidth = width * 0.2f
//                val handleHeight = height * 0.6f
//                val handleTop = height * 0.4f
//
//                moveTo(width * 0.5f - handleWidth / 2, handleTop)
//                lineTo(width * 0.5f + handleWidth / 2, handleTop)
//                lineTo(width * 0.5f + handleWidth / 2, height)
//                lineTo(width * 0.5f - handleWidth / 2, height)
//                close()
//            }
//
//            drawPath(
//                path = handlePath,
//                color = if (enabled) torchColor else MaterialTheme.colorScheme.outline.copy(alpha = 0.38f),
//                style = Fill
//            )
//
//            // Draw torch head
//            val torchHeadPath = Path().apply {
//                val headWidth = width * 0.4f
//                val headHeight = height * 0.3f
//                val headTop = height * 0.1f
//
//                moveTo(width * 0.5f - headWidth / 2, headTop + headHeight)
//                lineTo(width * 0.5f - headWidth / 2, headTop)
//                lineTo(width * 0.5f + headWidth / 2, headTop)
//                lineTo(width * 0.5f + headWidth / 2, headTop + headHeight)
//                close()
//            }
//
//            drawPath(
//                path = torchHeadPath,
//                color = if (enabled) torchColor else MaterialTheme.colorScheme.outline.copy(alpha = 0.38f),
//                style = Fill
//            )
//
//            // Draw flame if checked
//            if (flameScale > 0) {
//                val flameHeight = height * 0.4f * flameScale
//                val flameWidth = width * 0.3f * flameScale
//                val flameBottom = height * 0.1f
//
//                val flamePath = Path().apply {
//                    moveTo(width * 0.5f, flameBottom - flameHeight)
//
//                    // Right curve
//                    cubicTo(
//                        width * 0.5f + flameWidth * 0.5f, flameBottom - flameHeight * 0.7f,
//                        width * 0.5f + flameWidth, flameBottom - flameHeight * 0.3f,
//                        width * 0.5f + flameWidth * 0.5f, flameBottom
//                    )
//
//                    // Bottom
//                    lineTo(width * 0.5f - flameWidth * 0.5f, flameBottom)
//
//                    // Left curve
//                    cubicTo(
//                        width * 0.5f - flameWidth, flameBottom - flameHeight * 0.3f,
//                        width * 0.5f - flameWidth * 0.5f, flameBottom - flameHeight * 0.7f,
//                        width * 0.5f, flameBottom - flameHeight
//                    )
//
//                    close()
//                }
//
//                drawPath(
//                    path = flamePath,
//                    color = flameColor,
//                    style = Fill
//                )
//
//                // Draw flame glow
//                drawCircle(
//                    color = flameColor.copy(alpha = 0.3f),
//                    radius = flameWidth * 1.2f,
//                    center = Offset(width * 0.5f, flameBottom - flameHeight * 0.5f)
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
//@Preview(showBackground = true)
//@Composable
//fun GameSwitchPreview() {
//    GameTheme {
//        Surface(color = MaterialTheme.colorScheme.background) {
//            Column(modifier = Modifier.padding(16.dp)) {
//                var switchChecked by remember { mutableStateOf(false) }
//
//                GameSwitch(
//                    checked = switchChecked,
//                    onCheckedChange = { switchChecked = it },
//                    label = "Enable Notifications"
//                )
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                GameSwitch(
//                    checked = true,
//                    onCheckedChange = { },
//                    label = "Dark Mode"
//                )
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                GameSwitch(
//                    checked = false,
//                    onCheckedChange = { },
//                    enabled = false,
//                    label = "Disabled Option"
//                )
//
//                Spacer(modifier = Modifier.height(32.dp))
//
//                var torchChecked by remember { mutableStateOf(false) }
//
//                GameTorchSwitch(
//                    checked = torchChecked,
//                    onCheckedChange = { torchChecked = it },
//                    label = "Light Torch"
//                )
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                GameTorchSwitch(
//                    checked = true,
//                    onCheckedChange = { },
//                    label = "Torch Lit"
//                )
//            }
//        }
//    }
//}