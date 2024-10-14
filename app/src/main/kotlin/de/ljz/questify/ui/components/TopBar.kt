package de.ljz.questify.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ManageAccounts
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.sp
import de.ljz.questify.R
import de.ljz.questify.core.compose.UIModePreviews
import de.ljz.questify.ui.ds.theme.QuestifyTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    questItemCount: Int,
    drawerState: DrawerState
) {
    val scope = rememberCoroutineScope()
    var showMenu by remember { mutableStateOf(false) }

    val icon = "icon"

    val annotatedString = buildAnnotatedString {
        append("$questItemCount ")

        // Placeholder für das Icon
        appendInlineContent(icon, "[icon]")
    }

    val inlineContent = mapOf(
        icon to InlineTextContent(
            // Höhe und Breite des Icons festlegen
            placeholder = Placeholder(
                width = 16.sp,
                height = 16.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
            ),
            children = {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null, // Beschreibung des Icons
                )
            }
        )
    )

    TopAppBar(
        title = {
            Text(
                text = "Your Quests",
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Localized description"
                )
            }
        },
        actions = {
            TextButton(
                onClick = {
                    // TODO
                }
            ) {
                Text(
                    text = annotatedString,
                    inlineContent = inlineContent,
                )
            }
            IconButton(
                onClick = {
                    showMenu = !showMenu
                }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.no_profile_pic),
                    contentDescription = null,
                    modifier = Modifier.clip(CircleShape)
                )
            }

            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(
                    onClick = { /*TODO*/ },
                    text = {
                        Text(text = "Connect account")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.ManageAccounts,
                            contentDescription = "Profile"
                        )
                    }
                )
                DropdownMenuItem(
                    onClick = { /*TODO*/ },
                    text = {
                        Text(text = "Settings")
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Settings, contentDescription = "Settings")
                    }
                )
            }
        }
    )
}

@UIModePreviews
@Composable
private fun TopBarPreview() {
    QuestifyTheme {
        TopBar(
            questItemCount = 999,
            drawerState = DrawerState(initialValue = DrawerValue.Closed)
        )
    }
}