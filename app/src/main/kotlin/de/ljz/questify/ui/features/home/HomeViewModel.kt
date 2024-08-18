package de.ljz.questify.ui.features.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.core.coroutine.ContextProvider
import de.ljz.questify.data.sharedpreferences.SessionManager
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val contextProvider: ContextProvider,
  private val sessionManager: SessionManager
) : ViewModel() {
  
}