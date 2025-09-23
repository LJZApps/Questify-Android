package de.ljz.questify.core.data.models.descriptors

import de.ljz.questify.core.utils.SortingDirections

data class SortingDirectionItem(
    val text: String = "",
    val sortingDirection: SortingDirections = SortingDirections.ASCENDING
)