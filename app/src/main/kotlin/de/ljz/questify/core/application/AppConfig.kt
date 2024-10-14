package de.ljz.questify.core.application

import de.ljz.questify.BuildConfig

class AppConfig {
    fun isDebugMode() = BuildConfig.DEBUG
}