package de.ljz.questify.util

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

object NavBarConfig {
    var transparentNavBar by mutableStateOf<Boolean>(true) // Default value
}