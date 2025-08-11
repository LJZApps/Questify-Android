package de.ljz.questify.ui.ds.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// Define custom shapes for the app
val QuestifyShapes = Shapes(
    // Small components like buttons, chips, and small cards
    small = RoundedCornerShape(8.dp),
    
    // Medium components like medium-sized cards, dialogs
    medium = RoundedCornerShape(12.dp),
    
    // Large components like large cards, bottom sheets
    large = RoundedCornerShape(16.dp),
    
    // Extra large components like full-screen dialogs
    extraLarge = RoundedCornerShape(24.dp)
)