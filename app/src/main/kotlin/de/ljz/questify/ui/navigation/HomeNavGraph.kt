package de.ljz.questify.ui.navigation

import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph
@NavGraph
annotation class HomeNavGraph(
  val start: Boolean = false
)