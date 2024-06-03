package de.ljz.questify.core.main

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


object MainViewContract {
  data class State(
    val isLoggedIn: Boolean = false,
    val isSetupDone: Flow<Boolean> = flowOf(false)
  )

  sealed interface Action {

  }

  sealed interface Effect {

  }
}