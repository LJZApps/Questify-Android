package de.ljz.questify.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationRoute<T : Any>(
    val name: String,
    val route: T,
    val icon: ImageVector
)
