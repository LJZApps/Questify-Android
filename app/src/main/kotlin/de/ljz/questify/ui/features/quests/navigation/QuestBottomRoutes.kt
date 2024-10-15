package de.ljz.questify.ui.features.quests.navigation

import kotlinx.serialization.Serializable

@Serializable
object QuestBottomRoutes {
    @Serializable
    object AllQuests

    @Serializable
    object TodayQuests

    @Serializable
    object RepeatingQuests
}