package de.ljz.questify.core.utils

enum class Difficulty(
    val xpValue: Int,
    val pointsValue: Int,
) {
    EASY(
        xpValue = 10,
        pointsValue = 3,
    ),
    MEDIUM(
        xpValue = 20,
        pointsValue = 5,
    ),
    HARD(
        xpValue = 40,
        pointsValue = 10,
    );

    companion object {
        fun fromIndex(index: Int): Difficulty {
            return entries.getOrElse(index) { EASY } // Return EASY as a safe default
        }
    }
}
