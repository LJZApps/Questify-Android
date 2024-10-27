package de.ljz.questify.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.ljz.questify.core.compose.UIModePreviews

@Composable
fun DifficultyIconContainer(content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = Modifier.size(24.dp), // Einheitliche Container-Größe für Konsistenz
        contentAlignment = Alignment.Center,
        content = content
    )
}

// Die verschiedenen Schwierigkeitsgrade als Schwerter-Icons
@Composable
fun EasyIcon() {
    DifficultyIconContainer {
        SwordIcon()
    }
}

@Composable
fun MediumIcon() {
    DifficultyIconContainer {
        Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
            SwordIcon()
            SwordIcon()
        }
    }
}

@Composable
fun HardIcon() {
    DifficultyIconContainer {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SwordIcon()
            Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                SwordIcon()
                SwordIcon()
            }
        }
    }
}

@Composable
fun EpicIcon() {
    DifficultyIconContainer {
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                SwordIcon()
                SwordIcon()
            }
            Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                SwordIcon()
                SwordIcon()
            }
        }
    }
}

@Composable
fun SwordIcon() {
    val color = MaterialTheme.colorScheme.onBackground
    Canvas(modifier = Modifier.size(8.dp)) { // Kompaktes, kleines Schwert-Symbol
        rotate(degrees = 45f) {
            drawSword(color)
        }
    }
}


fun DrawScope.drawSword(color: Color) {
    val path = Path().apply {
        // Größere Klinge
        moveTo(size.width / 2f, 0f)
        lineTo(size.width * 0.65f, size.height * 0.6f)
        lineTo(size.width * 0.35f, size.height * 0.6f)
        close()
    }
    drawPath(path = path, color = color)

    // Größere Parierstange
    drawLine(
        color = color,
        start = androidx.compose.ui.geometry.Offset(size.width * 0.25f, size.height * 0.6f),
        end = androidx.compose.ui.geometry.Offset(size.width * 0.75f, size.height * 0.6f),
        strokeWidth = 1.5f
    )

    // Größerer Griff
    drawRect(
        color = color,
        topLeft = androidx.compose.ui.geometry.Offset(size.width * 0.45f, size.height * 0.6f),
        size = androidx.compose.ui.geometry.Size(width = size.width * 0.1f, height = size.height * 0.4f)
    )
}

// Vorschau
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

@UIModePreviews
@Composable
fun PreviewDifficultyIcons() {
    MaterialTheme {
        DifficultyIconsPreview()
    }
}