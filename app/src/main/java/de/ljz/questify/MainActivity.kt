package de.ljz.questify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.ljz.questify.ui.theme.QuestifyTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      QuestifyTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          Column (
            modifier = Modifier.padding(innerPadding)
          ) {
            Greeting(
              name = "Android",
            )
            Button(onClick = {  }) {
              Text(text = "Hallo")
            }
            LinearProgressIndicator(progress = 7.0f)
          }
        }
      }
    }
  }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(
    text = "Hello $name!",
    modifier = modifier
  )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  QuestifyTheme {
    Greeting("Android")
  }
}