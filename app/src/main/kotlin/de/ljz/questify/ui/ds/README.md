# Questify Game Design System

## Overview

The Questify Game Design System is a comprehensive UI framework designed to gamify the task management experience. It transforms everyday to-do items into epic quests through fantasy RPG-inspired visual elements, game-like interactions, and achievement-oriented UI components.

## Design Philosophy

The design system is built around four core principles:

1. **Fantasy/RPG Aesthetics**: Visual elements inspired by role-playing games
2. **Game-like Interactions**: Feedback that feels rewarding and engaging
3. **Achievement-oriented Components**: UI that celebrates progress and completion
4. **Immersive Experience**: A cohesive theme that transforms mundane tasks into adventures

## Components

### Theme

The design system provides two theme variants:

- `GameTheme`: The standard theme with rounded corners
- `GameThemeAlternative`: A more fantasy-styled theme with cut corners

```kotlin
// Use the standard theme
GameTheme {
    // Your UI content
}

// Or use the alternative theme
GameThemeAlternative {
    // Your UI content
}
```

### Colors

The color system is designed to evoke fantasy RPG games with:

- Rich, saturated colors for primary actions
- Magical accent colors for special elements
- Parchment-like backgrounds for light theme
- Dark, mysterious backgrounds for dark theme
- Clear color coding for quest difficulty levels

Key color extensions:
- `colorScheme.difficultyColor(difficulty)`: Get the appropriate color for a difficulty level
- `colorScheme.statusColor(completed, active)`: Get the appropriate color for a quest status

### Typography

The typography system uses game-inspired fonts:

- `QuestTitleFont` (Arcade): For titles and headers
- `GameBodyFont` (VT323): For body text and UI elements
- `PixelFont` (Press Start): For pixel-style elements
- `AltPixelFont` (Tine5): Alternative pixel font

### Shapes

The shape system includes unique game-inspired shapes:

- `MaterialTheme.shapes.scroll`: Scroll-like shape for quest cards
- `MaterialTheme.shapes.shield`: Shield-like shape for buttons
- `MaterialTheme.shapes.banner`: Banner-like shape for headers

Custom shape sizes can be created with:
- `MaterialTheme.shapes.scrollShape(cornerSize)`
- `MaterialTheme.shapes.shieldShape(cornerSize)`
- `MaterialTheme.shapes.bannerShape(cornerSize)`

## UI Components

### GameButton

A game-inspired button with a shield shape and animated press effect.

```kotlin
// Primary button
GameButton(
    onClick = { /* action */ },
    text = "Start Quest"
)

// Secondary button
GameButton(
    onClick = { /* action */ },
    text = "Cancel Quest",
    variant = GameButtonVariant.SECONDARY
)

// Tertiary button
GameButton(
    onClick = { /* action */ },
    text = "Skip Tutorial",
    variant = GameButtonVariant.TERTIARY
)
```

### QuestCard

A game-inspired card for displaying quests with a scroll shape.

```kotlin
QuestCard(
    title = "Defeat the Dragon",
    description = "Journey to the mountain and slay the dragon.",
    difficulty = 4,
    isCompleted = false,
    isActive = true,
    dueDate = "Tomorrow",
    reward = 500,
    onClick = { /* action */ }
)
```

## Usage Guidelines

1. **Consistency**: Use the provided components consistently throughout the app
2. **Hierarchy**: Use color and typography to establish visual hierarchy
3. **Feedback**: Provide clear feedback for user actions with animations and color changes
4. **Accessibility**: Ensure text remains readable despite the fantasy styling
5. **Theming**: Use the theme parameters to adapt to user preferences

## Migration

The legacy `QuestifyTheme` has been updated to forward to the new `GameTheme`. This allows for a gradual transition to the new design system without breaking existing code.

To fully adopt the new design system:

1. Replace any direct usage of `QuestifyTheme` with `GameTheme`
2. Replace standard Material components with game-inspired alternatives where appropriate
3. Update any custom components to use the new shapes, colors, and typography

## Examples

See the preview composables in each component file for usage examples:
- `GameButtonPreview` in GameButton.kt
- `QuestCardPreview` in QuestCard.kt