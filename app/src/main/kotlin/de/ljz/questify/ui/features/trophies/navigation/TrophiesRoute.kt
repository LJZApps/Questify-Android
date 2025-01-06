package de.ljz.questify.ui.features.trophies.navigation

import kotlinx.serialization.Serializable

@Serializable
object TrophiesRoute

@Serializable
object TrophyBottomRoutes {
    @Serializable
    object AllTrophiesRoute

    @Serializable
    object TrophyCategoriesRoute
}