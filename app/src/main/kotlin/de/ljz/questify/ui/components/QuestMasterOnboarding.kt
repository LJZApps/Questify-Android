package de.ljz.questify.ui.components

import android.media.SoundPool
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.ljz.questify.R
import de.ljz.questify.ui.features.getstarted.subpages.PixelSpeechBubbleShape
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun QuestMasterOnboarding(
    messages: List<String>,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val soundPool = remember { SoundPool.Builder().setMaxStreams(1).build() }
    val soundId = remember { soundPool.load(context, R.raw.typing, 1) }
    var currentIndex by remember { mutableIntStateOf(0) }
    var currentText by remember { mutableStateOf("") }
    var showContinueHint by remember { mutableStateOf(false) }

    LaunchedEffect(currentIndex) {
        currentText = ""
        showContinueHint = false
        val message = messages[currentIndex]
        for (char in message) {
            currentText += char
            delay(50) // Typing-Geschwindigkeit
            val pitch = Random.nextFloat() * (1.2f - 0.8f) + 0.8f
            soundPool.play(soundId, 1f, 1f, 1, 0, pitch) // Zufälliger Pitch
        }
        showContinueHint = true
    }

    DisposableEffect(Unit) {
        onDispose {
            soundPool.release()
        }
    }

    // Overlay für das Onboarding
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                if (currentIndex < messages.size - 1) {
                    currentIndex++
                } else {
                    onDismiss()
                }
            },
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally // Zentriert nur die Inhalte in der Column, nicht das Bild
        ) {
            // QuestMaster Bild linksbündig
            Image(
                painter = painterResource(id = R.drawable.quest_master),
                contentDescription = "Quest Master",
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.Start) // Bild linksbündig in der Column ausrichten
                    .padding(bottom = 16.dp)
            )

            // Sprechblase und Text zentriert
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, PixelSpeechBubbleShape)
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = currentText,
                        textAlign = TextAlign.Start,
                        fontSize = 10.sp,
                        fontFamily = FontFamily(Font(R.font.pressstart_regular)),
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                    if (showContinueHint) {
                        Text(
                            text = if (currentIndex != messages.lastIndex) "Tippe, um fortzufahren" else "Tippe, um anzufangen",
                            fontSize = 7.sp,
                            color = Color.Gray,
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(top = 8.dp),
                            fontFamily = FontFamily(Font(R.font.pressstart_regular))
                        )
                    }
                }
            }
        }
    }
}