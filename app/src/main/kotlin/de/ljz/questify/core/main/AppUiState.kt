package de.ljz.questify.core.main

import kotlinx.coroutines.flow.flowOf


data class AppUiState(
  val isLoggedIn: Boolean = false,
  val isSetupDone: Boolean = false,
)