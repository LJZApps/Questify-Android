package de.ljz.questify.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object GetStarted {
    @Serializable
    object Main
    data class Loginer(
        val test: String
    )
}

@Serializable
object GetStartedMain