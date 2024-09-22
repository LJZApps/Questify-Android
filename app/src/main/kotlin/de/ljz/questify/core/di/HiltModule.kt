package de.ljz.questify.core.di

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.hilt.ScreenModelKey
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoMap
import de.ljz.questify.ui.features.getstarted.GetStartedViewModel
import de.ljz.questify.ui.features.home.HomeScreenModel
import de.ljz.questify.ui.features.loginandregister.LoginScreenModel

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
  abstract fun bingHomeScreenModel(hiltListScreenModel: HomeScreenModel): ScreenModel

}