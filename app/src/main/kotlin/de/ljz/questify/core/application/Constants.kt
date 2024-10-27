package de.ljz.questify.core.application

/**
 * Logging tag: "Questify"
 */
const val TAG = "Questify"

enum class Difficulty {
    EASY,
    MEDIUM,
    HARD,
    EPIC;

    companion object {
        fun fromIndex(index: Int): Difficulty? {
            return Difficulty.entries.getOrNull(index)
        }
    }
}