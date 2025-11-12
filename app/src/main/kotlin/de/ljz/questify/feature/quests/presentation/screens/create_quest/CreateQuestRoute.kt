package de.ljz.questify.feature.quests.presentation.screens.create_quest

import de.ljz.questify.core.presentation.navigation.AppNavKey
import kotlinx.serialization.Serializable

@Serializable
data class CreateQuestRoute(
    val selectedCategoryIndex: Int? = null
): AppNavKey