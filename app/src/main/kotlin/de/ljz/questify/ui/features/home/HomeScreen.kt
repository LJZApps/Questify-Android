package de.ljz.questify.ui.features.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import de.ljz.questify.ui.navigation.HomeNavGraph

@HomeNavGraph(start = true)
@Composable
fun HomeScreen(
  navigator: NavController,
  vm: HomeViewModel = hiltViewModel()
) {
  
}