package de.ljz.questify.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import de.ljz.questify.core.compose.UIModePreviews
import de.ljz.questify.ui.features.quests.questdetail.navigation.QuestDetail

@Composable
fun QuestItem(
    id: Int,
    title: String,
    description: String,
    done: Boolean,
    onQuestChecked: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController? = null
) {
    ElevatedCard (
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        onClick = {
            navController?.navigate(QuestDetail(id))
        }
    ) {
        ListItem (
            headlineContent = {
                Text(
                    title
                )
            },
            supportingContent = {
                Text(description, maxLines = 1)
            },
            leadingContent = {
                Checkbox(
                    checked = done,
                    onCheckedChange = {
                        onQuestChecked()
                    }
                )
            },
            modifier = modifier.fillMaxWidth(),
            tonalElevation = 50.dp
        )
    }
}

@UIModePreviews
@Composable
private fun QuestItemPreview() {
    QuestItem(
        title = "Quest #1",
        description = "Quest description",
        done = false,
        onQuestChecked = {  },
        id = 1
    )
}