// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.jetbrains.kotlin.android) apply false
  alias(libs.plugins.jetbrainsCompose) apply false
  alias(libs.plugins.compose.compiler) apply false

  id("com.android.library") version "8.5.2" apply false
  id("com.google.devtools.ksp") version "2.0.0-1.0.21" apply false
  id("com.google.gms.google-services") version "4.4.2" apply false
  id("com.google.firebase.crashlytics") version "3.0.2" apply false
  id("androidx.navigation.safeargs") version "2.7.7" apply false
  id("com.google.dagger.hilt.android") version "2.51.1" apply false
  kotlin("plugin.serialization") version "1.9.23" apply false
}