// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.jetbrains.kotlin.android) apply false
  alias(libs.plugins.jetbrainsCompose) apply false
  alias(libs.plugins.compose.compiler) apply false

  id("com.android.library") version "8.6.1" apply false
  id("com.google.gms.google-services") version "4.4.2" apply false
  id("com.google.firebase.crashlytics") version "3.0.2" apply false
  id("androidx.navigation.safeargs") version "2.8.2" apply false
  id("com.google.dagger.hilt.android") version "2.52" apply false
  kotlin("plugin.serialization") version "1.9.23" apply false
}