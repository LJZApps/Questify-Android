package de.ljz.questify.ui.features.remindagain

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dagger.hilt.android.AndroidEntryPoint
import de.ljz.questify.domain.repositories.QuestNotificationRepository
import de.ljz.questify.domain.repositories.QuestRepository
import de.ljz.questify.ui.ds.theme.QuestifyTheme
import okhttp3.internal.toLongOrDefault
import javax.inject.Inject

@AndroidEntryPoint
class RemindAgainActivity : ComponentActivity() {

    @Inject
    lateinit var questRepository: QuestRepository

    @Inject
    lateinit var questNotificationRepository: QuestNotificationRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val questId = intent.getIntExtra("questId", -1)
        val notificationId = intent.getIntExtra("notificationId", 0)

        setContent {
            val viewModel: RemindAgainViewModel by viewModels()
            val context = LocalContext.current

            QuestifyTheme {
                RemindAgainPopUp(
                    onReminderTimeSelected = { remindAgainTime, minutes ->
                        viewModel.createQuestNotification(
                            questId = questId,
                            triggerTime = System.currentTimeMillis() + remindAgainTime
                        )

                        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                        notificationManager.cancel(notificationId)

                        Toast.makeText(
                            context,
                            "Du wirst in $minutes Minuten erneut erinnert.",
                            Toast.LENGTH_LONG
                        ).show()

                        finish()
                    },
                    onDismiss = {
                        finish()
                    }
                )
            }
        }
    }
}

@Composable
fun RemindAgainPopUp(
    onReminderTimeSelected: (Long, Long) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedTime by remember { mutableIntStateOf(5) }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var isCustomTimeSelected by remember { mutableStateOf(false) }
    var customTime by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        Dialog(
            onDismissRequest = onDismiss
        ) {
            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                tonalElevation = 6.dp,
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable(enabled = false) {}
                ) {
                    Text("Erneut erinnern in...", style = MaterialTheme.typography.headlineSmall)

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable { isDropdownExpanded = true }
                            .background(
                                Color.Gray.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(4.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (isCustomTimeSelected) "${customTime.toLongOrDefault(0)} Minuten" else "$selectedTime Minuten",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = isDropdownExpanded,
                        onDismissRequest = { isDropdownExpanded = false },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                selectedTime = 5
                                isDropdownExpanded = false
                                isCustomTimeSelected = false
                            },
                            text = { Text("5 Minuten") }
                        )
                        DropdownMenuItem(
                            onClick = {
                                selectedTime = 10
                                isDropdownExpanded = false
                                isCustomTimeSelected = false
                            },
                            text = { Text("10 Minuten") }
                        )
                        DropdownMenuItem(
                            onClick = {
                                selectedTime = 15
                                isDropdownExpanded = false
                                isCustomTimeSelected = false
                            },
                            text = { Text("15 Minuten") }
                        )

                        HorizontalDivider()

                        DropdownMenuItem(
                            onClick = {
                                isDropdownExpanded = false
                                isCustomTimeSelected = true
                            },
                            text = { Text("Benutzerdefiniert") }
                        )
                    }

                    if (isCustomTimeSelected) {
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = customTime,
                            onValueChange = { customTime = it },
                            label = { Text("Benutzerdefinierte Minuten") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            placeholder = { Text("z.B. 20") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            val timeInMillis = if (isCustomTimeSelected) {
                                customTime.toLongOrDefault(0).times(60 * 1000)
                            } else {
                                (selectedTime * 60 * 1000).toLong()
                            }
                            onReminderTimeSelected(timeInMillis, if (isCustomTimeSelected) customTime.toLongOrDefault(0) else selectedTime.toLong())
                            onDismiss()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !(isCustomTimeSelected && customTime.toLongOrDefault(0).toInt() == 0)
                    ) {
                        Text("Erinnerung setzen")
                    }
                }
            }
        }
    }
}