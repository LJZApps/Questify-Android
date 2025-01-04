package de.ljz.questify.ui.features.get_started.sub_pages

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.ljz.questify.core.compose.UIModePreviews

@Composable
fun AdventureScreen() {
    val isPressed = remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    val animatedSize by animateDpAsState(
        targetValue = if (isPressed.value) maxOf(screenWidth, screenHeight) else 100.dp,
        animationSpec = tween(durationMillis = 3000, easing = LinearOutSlowInEasing), label = "AnimateSize"
    )

    val animatedShape by animateDpAsState(
        targetValue = if (isPressed.value) 0.dp else 50.dp, // Kreis -> Rechteck
        animationSpec = tween(durationMillis = 3000, easing = LinearOutSlowInEasing), label = "AnimateShape"
    )

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            text = "Halte den Knopf gedrückt, um dein Abenteuer zu starten",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Box(
            modifier = Modifier
                .size(animatedSize)
                .clip(RoundedCornerShape(animatedShape))
                .background(MaterialTheme.colorScheme.primary)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            isPressed.value = true
                            tryAwaitRelease()
                            isPressed.value = false
                        }
                    )
                }
        )
    }
}

@UIModePreviews
@Composable
private fun AdventureScreenPreview() {
    AdventureScreen()
}