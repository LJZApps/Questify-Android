package de.ljz.questify.wearos.core.presentation.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WearApp()
        }
    }
}

@Composable
fun WearApp() {
    QuestifyWearTheme {
        Scaffold(
            timeText = { TimeText() }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Hallo Questify!",
                    style = MaterialTheme.typography.title1
                )
            }
        }
    }
}

@Composable
fun QuestifyWearTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        // Hier könntest du Farben, Typografie etc. anpassen
        content = content
    )
}