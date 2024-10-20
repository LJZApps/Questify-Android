package de.ljz.questify.ui.features.quests.viewquests.navigation

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