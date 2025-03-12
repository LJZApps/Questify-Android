package de.ljz.questify.ui.ds.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import de.ljz.questify.ui.ds.theme.scroll

/**
 * Game-inspired date and time pickers for Questify
 * 
 * These picker components use scroll-like shapes and game-inspired styling.
 * They include:
 * - GameDatePickerDialog: Date picker with game-themed styling
 * - GameTimePickerDialog: Time picker with game-themed styling
 * 
 * All pickers support:
 * - Scroll shape by default (can be customized)
 * - Fantasy-inspired colors
 * - Customizable appearance
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDatePickerDialog(
    onDismissRequest: () -> Unit,
    onDateSelected: (Long) -> Unit,
    modifier: Modifier = Modifier,
    initialSelectedDateMillis: Long? = null,
    title: String = "Select Date",
    confirmButtonText: String = "Confirm",
    dismissButtonText: String = "Cancel",
    shape: Shape = MaterialTheme.shapes.scroll
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialSelectedDateMillis,
        initialDisplayMode = DisplayMode.Picker
    )
    
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = modifier
                .shadow(8.dp, shape)
                .clip(shape)
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = shape
                )
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    shape = shape
                )
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title with icon
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }
                
                // Divider
                GameDivider(
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                // Date Picker
                DatePicker(
                    state = datePickerState,
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    colors = DatePickerDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                        headlineContentColor = MaterialTheme.colorScheme.onSurface,
                        weekdayContentColor = MaterialTheme.colorScheme.primary,
                        subheadContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        yearContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        currentYearContentColor = MaterialTheme.colorScheme.primary,
                        selectedYearContentColor = MaterialTheme.colorScheme.onPrimary,
                        selectedYearContainerColor = MaterialTheme.colorScheme.primary,
                        dayContentColor = MaterialTheme.colorScheme.onSurface,
                        selectedDayContentColor = MaterialTheme.colorScheme.onPrimary,
                        selectedDayContainerColor = MaterialTheme.colorScheme.primary,
                        todayContentColor = MaterialTheme.colorScheme.primary,
                        todayDateBorderColor = MaterialTheme.colorScheme.primary
                    )
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    GameButton(
                        onClick = onDismissRequest,
                        text = dismissButtonText,
                        variant = GameButtonVariant.SECONDARY,
                        modifier = Modifier.weight(1f)
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    GameButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let { onDateSelected(it) }
                        },
                        text = confirmButtonText,
                        modifier = Modifier.weight(1f),
                        enabled = datePickerState.selectedDateMillis != null
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameTimePickerDialog(
    onDismissRequest: () -> Unit,
    onTimeSelected: (Int, Int) -> Unit,
    modifier: Modifier = Modifier,
    initialHour: Int = 0,
    initialMinute: Int = 0,
    title: String = "Select Time",
    confirmButtonText: String = "Confirm",
    dismissButtonText: String = "Cancel",
    shape: Shape = MaterialTheme.shapes.scroll,
    is24Hour: Boolean = true
) {
    val timePickerState = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute,
        is24Hour = is24Hour
    )
    
    var showingTimePicker by remember { mutableStateOf(true) }
    
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = modifier
                .shadow(8.dp, shape)
                .clip(shape)
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = shape
                )
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    shape = shape
                )
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title with icon
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }
                
                // Divider
                GameDivider(
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                // Time Picker
                if (showingTimePicker) {
                    TimePicker(
                        state = timePickerState,
                        colors = TimePickerDefaults.colors(
                            clockDialColor = MaterialTheme.colorScheme.surfaceVariant,
                            clockDialSelectedContentColor = MaterialTheme.colorScheme.onPrimary,
                            clockDialUnselectedContentColor = MaterialTheme.colorScheme.onSurface,
                            selectorColor = MaterialTheme.colorScheme.primary,
                            containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            periodSelectorBorderColor = MaterialTheme.colorScheme.primary,
                            periodSelectorSelectedContainerColor = MaterialTheme.colorScheme.primary,
                            periodSelectorUnselectedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                            periodSelectorSelectedContentColor = MaterialTheme.colorScheme.onPrimary,
                            periodSelectorUnselectedContentColor = MaterialTheme.colorScheme.onSurface,
                            timeSelectorSelectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            timeSelectorUnselectedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                            timeSelectorSelectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            timeSelectorUnselectedContentColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                } else {
                    TimeInput(
                        state = timePickerState,
                        colors = TimePickerDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            periodSelectorBorderColor = MaterialTheme.colorScheme.primary,
                            periodSelectorSelectedContainerColor = MaterialTheme.colorScheme.primary,
                            periodSelectorUnselectedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                            periodSelectorSelectedContentColor = MaterialTheme.colorScheme.onPrimary,
                            periodSelectorUnselectedContentColor = MaterialTheme.colorScheme.onSurface,
                            timeSelectorSelectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            timeSelectorUnselectedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                            timeSelectorSelectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            timeSelectorUnselectedContentColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
                
                // Toggle button
                GameButton(
                    onClick = { showingTimePicker = !showingTimePicker },
                    text = if (showingTimePicker) "Switch to Text Input" else "Switch to Clock",
                    variant = GameButtonVariant.TERTIARY,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    GameButton(
                        onClick = onDismissRequest,
                        text = dismissButtonText,
                        variant = GameButtonVariant.SECONDARY,
                        modifier = Modifier.weight(1f)
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    GameButton(
                        onClick = {
                            onTimeSelected(timePickerState.hour, timePickerState.minute)
                        },
                        text = confirmButtonText,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}