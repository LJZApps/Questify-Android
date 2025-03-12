package de.ljz.questify.ui.ds.theme

import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

/**
 * Game-inspired shapes for Questify
 * 
 * This shape system is designed to evoke fantasy RPG game interfaces with:
 * - Scroll-like shapes for cards and containers
 * - Shield-like shapes for buttons
 * - Banner-like shapes for headers
 * - Unique quest card shapes
 */

// Custom shape for quest cards with a scroll-like appearance
class ScrollShape(private val cornerSize: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            // Start from top-left with a small curve
            moveTo(0f, cornerSize)
            quadraticBezierTo(0f, 0f, cornerSize, 0f)
            
            // Top edge with a slight wave
            lineTo(size.width - cornerSize, 0f)
            quadraticBezierTo(size.width, 0f, size.width, cornerSize)
            
            // Right edge straight down
            lineTo(size.width, size.height - cornerSize)
            
            // Bottom-right corner
            quadraticBezierTo(size.width, size.height, size.width - cornerSize, size.height)
            
            // Bottom edge with a slight wave
            lineTo(cornerSize, size.height)
            
            // Bottom-left corner
            quadraticBezierTo(0f, size.height, 0f, size.height - cornerSize)
            
            // Close the path
            close()
        }
        return Outline.Generic(path)
    }
}

// Custom shape for buttons with a shield-like appearance
class ShieldShape(private val cornerSize: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            // Start from top-left
            moveTo(0f, cornerSize)
            quadraticBezierTo(0f, 0f, cornerSize, 0f)
            
            // Top edge
            lineTo(size.width - cornerSize, 0f)
            quadraticBezierTo(size.width, 0f, size.width, cornerSize)
            
            // Right edge with a slight inward curve
            lineTo(size.width, size.height * 0.7f)
            quadraticBezierTo(
                size.width, size.height,
                size.width * 0.5f, size.height
            )
            
            // Left edge with a slight inward curve
            quadraticBezierTo(
                0f, size.height,
                0f, size.height * 0.7f
            )
            
            // Close the path
            close()
        }
        return Outline.Generic(path)
    }
}

// Custom shape for headers with a banner-like appearance
class BannerShape(private val cornerSize: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            // Start from top-left
            moveTo(0f, 0f)
            
            // Top edge
            lineTo(size.width, 0f)
            
            // Right edge with a triangle cut
            lineTo(size.width, size.height - cornerSize)
            lineTo(size.width - cornerSize, size.height)
            
            // Bottom edge
            lineTo(cornerSize, size.height)
            
            // Left edge with a triangle cut
            lineTo(0f, size.height - cornerSize)
            
            // Close the path
            close()
        }
        return Outline.Generic(path)
    }
}

// Game-inspired shapes
val GameShapes = Shapes(
    // Small components like chips, small buttons
    small = RoundedCornerShape(4.dp),
    
    // Medium components like cards, dialogs
    medium = RoundedCornerShape(8.dp),
    
    // Large components like bottom sheets, large cards
    large = RoundedCornerShape(12.dp),
    
    // Extra large components
    extraLarge = RoundedCornerShape(16.dp)
)

// Alternative shapes with cut corners for a more fantasy feel
val GameShapesAlternative = Shapes(
    small = CutCornerShape(4.dp),
    medium = CutCornerShape(8.dp),
    large = CutCornerShape(12.dp),
    extraLarge = CutCornerShape(16.dp)
)

// Extension properties for custom shapes
val Shapes.scroll: Shape
    get() = ScrollShape(8f)

val Shapes.shield: Shape
    get() = ShieldShape(8f)

val Shapes.banner: Shape
    get() = BannerShape(16f)

// Extension functions for different sizes of custom shapes
fun Shapes.scrollShape(cornerSize: Float = 8f): Shape = ScrollShape(cornerSize)
fun Shapes.shieldShape(cornerSize: Float = 8f): Shape = ShieldShape(cornerSize)
fun Shapes.bannerShape(cornerSize: Float = 16f): Shape = BannerShape(cornerSize)