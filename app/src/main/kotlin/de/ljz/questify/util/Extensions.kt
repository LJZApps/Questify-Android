package de.ljz.questify.util

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput


enum class ButtonState { Pressed, Idle }

fun Modifier.bounceClick() = composed {
  var buttonState by remember { mutableStateOf(ButtonState.Idle) }
  val scale by animateFloatAsState(if (buttonState == ButtonState.Pressed) 0.70f else 1f)

  this
    .graphicsLayer {
      scaleX = scale
      scaleY = scale
    }
    .clickable(
      interactionSource = remember { MutableInteractionSource() },
      indication = null,
      onClick = { }
    )
    .pointerInput(buttonState) {
      awaitPointerEventScope {
        buttonState = if (buttonState == ButtonState.Pressed) {
          waitForUpOrCancellation()
          ButtonState.Idle
        } else {
          awaitFirstDown(false)
          ButtonState.Pressed
        }
      }
    }
}

fun Modifier.typewriterAnimation(): Modifier = composed {
  val animatedProgress = remember { Animatable(0f) }

  this.then(
    Modifier.drawWithContent {

      drawContent()

      val textWidth = drawContext.size.toDpSize().width.value * animatedProgress.value

      with(drawContext.canvas.nativeCanvas) {
        clipRect(
          left = 0f,
          top = 0f,
          right = textWidth,
          bottom = size.height.toFloat(),
          clipOp = ClipOp.Intersect
        ) {
          drawRect(Color.White)
        }
      }
    }
  )
}