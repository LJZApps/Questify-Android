package de.ljz.questify.core.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DifficultyIconContainer(content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = Modifier.size(24.dp),
        contentAlignment = Alignment.Center,
        content = content
    )
}

@Composable
fun EasyIcon(
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.primary
) {
    DifficultyIconContainer {
        SwordIcon(modifier, tint)
    }
}

@Composable
fun MediumIcon(
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.primary
) {
    DifficultyIconContainer {
        Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
            SwordIcon(modifier, tint)
            SwordIcon(modifier, tint)
        }
    }
}

@Composable
fun HardIcon(
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.primary
) {
    DifficultyIconContainer {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SwordIcon(modifier, tint)
            Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                SwordIcon(modifier, tint)
                SwordIcon(modifier, tint)
            }
        }
    }
}

@Composable
fun EpicIcon(
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.primary
) {
    DifficultyIconContainer {
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                SwordIcon(modifier, tint)
                SwordIcon(modifier, tint)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                SwordIcon(modifier, tint)
                SwordIcon(modifier, tint)
            }
        }
    }
}

@Composable
fun SwordIcon(
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.primary
) {
    Canvas(modifier = modifier.size(8.dp)) {
        rotate(degrees = 45f) {
            drawSword(tint)
        }
    }
}


fun DrawScope.drawSword(color: Color) {
    val path = Path().apply {
        moveTo(size.width / 2f, 0f)
        lineTo(size.width * 0.65f, size.height * 0.6f)
        lineTo(size.width * 0.35f, size.height * 0.6f)
        close()
    }
    drawPath(path = path, color = color)

    drawLine(
        color = color,
        start = Offset(size.width * 0.25f, size.height * 0.6f),
        end = Offset(size.width * 0.75f, size.height * 0.6f),
        strokeWidth = 1.5f
    )

    drawRect(
        color = color,
        topLeft = Offset(size.width * 0.45f, size.height * 0.6f),
        size = Size(width = size.width * 0.1f, height = size.height * 0.4f)
    )
}

@Composable
fun DifficultyIconsPreview() {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            EasyIcon()
            Text("Easy", fontSize = 10.sp, color = MaterialTheme.colorScheme.onBackground)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            MediumIcon()
            Text("Medium", fontSize = 10.sp, color = MaterialTheme.colorScheme.onBackground)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            HardIcon()
            Text("Hard", fontSize = 10.sp, color = MaterialTheme.colorScheme.onBackground)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            EpicIcon()
            Text("Epic", fontSize = 10.sp, color = MaterialTheme.colorScheme.onBackground)
        }
    }
}

@Preview
@Composable
fun PreviewDifficultyIcons() {
    MaterialTheme {
        DifficultyIconsPreview()
    }
}