package de.ljz.questify.feature.quests.presentation.screens.create_quest

import kotlinx.serialization.Serializable

@Serializable
data class CreateQuestRoute(
    val selectedCategoryIndex: Int? = null
)