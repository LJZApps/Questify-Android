package de.ljz.questify.core.application

import de.ljz.questify.BuildConfig
import javax.inject.Inject

class AppConfig @Inject constructor() {
  fun isDebugMode() = BuildConfig.DEBUG
}