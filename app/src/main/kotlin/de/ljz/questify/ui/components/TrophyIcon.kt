package de.ljz.questify.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import de.ljz.questify.util.getTrophyIconByName

@Composable
fun TrophyIcon(
    iconName: String,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current
) {
    Icon(
        imageVector = getTrophyIconByName(iconName),
        contentDescription = iconName,
        modifier = modifier,
        tint = tint
    )
}