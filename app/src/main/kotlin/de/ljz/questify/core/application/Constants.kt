package de.ljz.questify.core.application

import kotlinx.serialization.Serializable

/**
 * Logging tag: "Questify"
 */
const val TAG = "Questify"

enum class Difficulty {
    NONE,
    EASY,
    MEDIUM,
    HARD,
    EPIC;

    companion object {
        fun fromIndex(index: Int): Difficulty {
            return Difficulty.entries[index]
        }
    }
}

enum class QuestSorting {
    ID,
    TITLE,
    NOTES,
    DUE_DATE,
    DONE
}

enum class SortingDirections {
    ASCENDING,
    DESCENDING;
}

@Serializable
sealed class ReminderTime(val minutes: Int) {
    @Serializable
    object MIN_5 : ReminderTime(5)

    @Serializable
    object MIN_10 : ReminderTime(10)

    @Serializable
    object MIN_15 : ReminderTime(15)

    @Serializable
    class CUSTOM(val customMinutes: Int) : ReminderTime(customMinutes)

    @Serializable
    companion object {
        fun fromMinutes(minutes: Int): ReminderTime {
            return when (minutes) {
                5 -> MIN_5
                10 -> MIN_10
                15 -> MIN_15
                else -> CUSTOM(minutes)
            }
        }
    }
}

enum class AddingDateTimeState {
    NONE,
    DATE,
    TIME;
}