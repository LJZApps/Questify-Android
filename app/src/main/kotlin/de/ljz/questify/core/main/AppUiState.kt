package de.ljz.questify.core.main

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


data class AppUiState(
  val isLoggedIn: Boolean = false,
  val isSetupDone: Flow<Boolean> = flowOf(false),
)