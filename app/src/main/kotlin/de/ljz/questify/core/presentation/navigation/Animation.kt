package de.ljz.questify.core.presentation.navigation

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith


fun scaleContentTransform(
    direction: ScaleTransitionDirection = ScaleTransitionDirection.INWARDS,
    durationMillis: Int = 220,
    delayMillis: Int = 90
): ContentTransform {
    val inwards = (direction == ScaleTransitionDirection.INWARDS)

    val enter: EnterTransition = scaleIn(
        initialScale = if (inwards) 1.1f else 0.9f,
        animationSpec = tween(durationMillis = durationMillis, delayMillis = delayMillis, easing = FastOutSlowInEasing)
    ) + fadeIn(animationSpec = tween(durationMillis = durationMillis, delayMillis = delayMillis, easing = FastOutSlowInEasing))

    val exit: ExitTransition = scaleOut(
        targetScale = if (inwards) 0.9f else 1.1f,
        animationSpec = tween(durationMillis = durationMillis, delayMillis = delayMillis, easing = FastOutSlowInEasing)
    ) + fadeOut(animationSpec = tween(durationMillis = durationMillis, delayMillis = delayMillis, easing = FastOutSlowInEasing))

    // EnterTransition togetherWith ExitTransition => ContentTransform
    return enter togetherWith exit
}

enum class ScaleTransitionDirection {
    INWARDS, OUTWARDS
}