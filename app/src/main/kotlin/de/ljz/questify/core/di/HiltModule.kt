package de.ljz.questify.core.di

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.ScreenModelKey
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoMap
import de.ljz.questify.ui.features.getstarted.GetStartedViewModel
import de.ljz.questify.ui.features.home.HomeScreenModel
import de.ljz.questify.ui.features.loginandregister.LoginScreenModel
import de.ljz.questify.ui.features.register.RegisterScreen
import de.ljz.questify.ui.features.register.RegisterScreenModel

@Module
@InstallIn(ActivityComponent::class)
abstract class HiltModule {

  @Binds
  @IntoMap
  @ScreenModelKey(GetStartedViewModel::class)
  abstract fun bindGetStartedScreenModel(hiltListScreenModel: GetStartedViewModel): ScreenModel

  @Binds
  @IntoMap
  @ScreenModelKey(LoginScreenModel::class)
  abstract fun bindLoginScreenModel(hiltListScreenModel: LoginScreenModel): ScreenModel

  @Binds
  @IntoMap
  @ScreenModelKey(HomeScreenModel::class)
  abstract fun bindHomeScreenModel(hiltListScreenModel: HomeScreenModel): ScreenModel

  @Binds
  @IntoMap
  @ScreenModelKey(RegisterScreenModel::class)
  abstract fun bindRegisterScreenModel(hiltRegisterScreenModel: RegisterScreenModel): ScreenModel

}