package de.ljz.questify.feature.onboarding.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.ljz.questify.core.presentation.theme.QuestifyTheme

@Composable
fun TutorialQuestComponent(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    icon: @Composable (() -> Unit)
) {
    OutlinedCard(
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
    ) {
        ListItem(
            modifier = Modifier
                .fillMaxWidth(),
            colors = ListItemDefaults.colors(
                containerColor = Color.Transparent
            ),
            headlineContent = {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            },
            supportingContent = {
                Text(
                    text = description
                )
            },
            leadingContent = {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceContainer
                        )
                        .padding(8.dp)
                ) {
                    icon()
                }
            }
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun TutorialQuestComponentPreview() {
    QuestifyTheme {
        TutorialQuestComponent(
            modifier = Modifier.fillMaxWidth(),
            title = "Quests erstellen",
            description = "Teile deine Ziele in umsetzbare Aufgaben auf",
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = null
                )
            }
        )
    }
}