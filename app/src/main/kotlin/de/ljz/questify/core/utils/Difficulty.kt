package de.ljz.questify.core.utils

enum class Difficulty(val rewardValue: Int) {
    EASY(10),
    MEDIUM(25),
    HARD(50);

    companion object {
        fun fromIndex(index: Int): Difficulty {
            return entries[index]
        }
    }
}