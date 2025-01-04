package de.ljz.questify.ui.features.main.components

data class NavigationCategory<T : Any>(
    val title: String,
    /**
     * Disabled/Enable all features in this category
     */
    val featuresEnabled: Boolean = true,
    val showTitle: Boolean = true,
    val items: List<NavigationItem<T>>,
)
