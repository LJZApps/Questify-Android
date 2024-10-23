package de.ljz.questify.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ListItem
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import de.ljz.questify.core.compose.UIModePreviews

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
    val context = LocalContext.current
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when(it) {
                SwipeToDismissBoxValue.StartToEnd -> {}
                SwipeToDismissBoxValue.EndToStart -> {}
                SwipeToDismissBoxValue.Settled -> return@rememberSwipeToDismissBoxState false
            }
            return@rememberSwipeToDismissBoxState true
        },
        // positional threshold of 25%
        positionalThreshold = { it * .25f }
    )

/*    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier,
        backgroundContent = { DismissBackground(dismissState)},
        content = {*/
            ElevatedCard (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                /*onClick = {
                    navController?.navigate(QuestDetail(id))
                }*/
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
    /*})*/
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