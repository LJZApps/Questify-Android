package de.ljz.questify.ui.features.get_started.sub_pages

import android.media.SoundPool
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import de.ljz.questify.R
import de.ljz.questify.ui.features.get_started.GetStartedViewModel
import de.ljz.questify.ui.features.main.navigation.MainRoute
import de.ljz.questify.util.NavBarConfig
import de.ljz.questify.util.bounceClick
import kotlinx.coroutines.delay
import kotlin.random.Random

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GetStartedMainScreen(
    viewModel: GetStartedViewModel = hiltViewModel(),
    navController: NavHostController
) {
    LaunchedEffect(Unit) {
        NavBarConfig.transparentNavBar = true
    }

    val context = LocalContext.current

    val messages = listOf(
        "Willkommen, edler Suchender! Ich bin der Quest Master, dein weiser Begleiter auf dieser Reise.",
        "Zusammen werden wir deine Aufgaben in Quests verwandeln und verborgene Kräfte entfesseln.",
        "Questify wird deinen Alltag in ein episches Abenteuer verwandeln – voller Prüfungen und Geheimnisse.",
        "Bist du bereit, deinen Pfad zu erleuchten und dein wahres Potenzial zu entfalten?",
    )

    var currentIndex by remember { mutableIntStateOf(0) }
    var currentText by remember { mutableStateOf("") }
    var showContinueHint by remember { mutableStateOf(false) }
    var showButton by remember { mutableStateOf(false) }

    val soundPool = remember {
        SoundPool.Builder().setMaxStreams(1).build()
    }
    val soundId = remember { soundPool.load(context, R.raw.typing, 1) }

    LaunchedEffect(currentIndex) {
        currentText = ""
        showContinueHint = false
        showButton = false
        val message = messages[currentIndex]
        for (i in message.indices) {
            currentText += message[i]
            delay(40)

            val pitch = Random.nextFloat() * (1.2f - 0.8f) + 0.8f
            soundPool.play(soundId, 1f, 1f, 1, 0, pitch)
        }
        if (currentIndex == messages.lastIndex) {
            showButton = true
        } else {
            showContinueHint = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                enabled = showContinueHint && currentIndex < messages.size - 1,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                if (showContinueHint) {
                    currentIndex++
                    if (currentIndex == messages.size - 1) {
                        showButton = true
                    }
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.quest_master),
                contentDescription = "Quest Master",
                modifier = Modifier
                    .size(250.dp)
                    .padding(bottom = 16.dp)
            )

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
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.pressstart_regular)),
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth()
                    )
                    AnimatedVisibility(visible = showContinueHint, enter = fadeIn(), exit = fadeOut(animationSpec = tween(0))) {
                        Text(
                            text = "Tippe, um fortzufahren",
                            fontSize = 7.sp,
                            color = Color.Gray,
                            modifier = Modifier.align(Alignment.End).padding(top = 8.dp),
                            fontFamily = FontFamily(Font(R.font.pressstart_regular))
                        )
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn(animationSpec = tween(600)) + scaleIn(initialScale = 0.6f, animationSpec = tween(600, easing = EaseOut)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            Button(
                onClick = {
                    viewModel.setSetupDone()
                    navController.navigate(MainRoute)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .bounceClick()
            ) {
                Text(
                    text = "Ja, ich bin bereit!",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.pressstart_regular))
                )
            }
        }
    }
}

val PixelSpeechBubbleShape: Shape = GenericShape { size, _ ->
    val cornerRadius = 25f
    val peakStartX = size.width / 4

    moveTo(0f, cornerRadius)
    lineTo(0f, size.height - cornerRadius)
    lineTo(cornerRadius, size.height)
    lineTo(size.width - cornerRadius, size.height)
    lineTo(size.width, size.height - cornerRadius)
    lineTo(size.width, cornerRadius)
    lineTo(size.width - cornerRadius, 0f)
    lineTo(peakStartX + 10f, 0f)
    lineTo(peakStartX, -10f)
    lineTo(peakStartX - 10f, 0f)
    lineTo(cornerRadius, 0f)
    close()
}