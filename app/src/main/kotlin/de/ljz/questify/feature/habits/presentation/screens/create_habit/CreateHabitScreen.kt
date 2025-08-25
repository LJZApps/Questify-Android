package de.ljz.questify.feature.habits.presentation.screens.create_habit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.ljz.questify.feature.habits.data.models.HabitType

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CreateHabitScreen(

) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            val selectedTypes = remember { mutableStateListOf<HabitType>() }
            val habitTypeOptions = HabitType.entries.toTypedArray()

            Row(
                Modifier.padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
            ) {
                val modifiers = listOf(Modifier.weight(1f), Modifier.weight(1f))
                habitTypeOptions.forEachIndexed { index, habitType ->
                    ToggleButton(
                        checked = selectedTypes.contains(habitType),
                        onCheckedChange = { isChecked ->
                            if (isChecked) {
                                selectedTypes.add(habitType)
                            } else {
                                selectedTypes.remove(habitType)
                            }
                        },
                        modifier = modifiers[index],
                        shapes =
                            when (index) {
                                0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                                habitTypeOptions.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                                else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                            },
                    ) {
                        Icon(
                            imageVector = if(habitType == HabitType.POSITIVE) Icons.Default.Add else Icons.Default.Remove,
                            contentDescription = null,
                        )
                        Spacer(Modifier.size(ToggleButtonDefaults.IconSpacing))
                        Text(
                            text = if (habitType == HabitType.POSITIVE) "Positiv" else "Negativ"
                        )
                    }
                }
            }

            val options = listOf("Täglich", "Wöchentlich", "Monatlich")
            var selectedIndex by remember { mutableIntStateOf(0) }

            Row(
                Modifier.padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
            ) {
                options.forEachIndexed { index, label ->
                    ToggleButton(
                        checked = selectedIndex == index,
                        onCheckedChange = { selectedIndex = index },
                        modifier = Modifier.weight(1f),
                        shapes =
                            when (index) {
                                0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                                options.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                                else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                            },
                    ) {
                        Text(label)
                    }
                }
            }
        }
    }
}