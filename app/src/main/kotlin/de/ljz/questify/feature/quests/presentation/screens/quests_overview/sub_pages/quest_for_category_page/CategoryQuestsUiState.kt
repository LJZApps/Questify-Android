package de.ljz.questify.feature.quests.presentation.screens.quests_overview.sub_pages.quest_for_category_page

import de.ljz.questify.feature.quests.data.relations.QuestWithSubQuests

data class CategoryQuestsUiState(
    val quests: List<QuestWithSubQuests> = emptyList(),
    val isLoading: Boolean = true
)
