// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.jetbrains.kotlin.android) apply false

  id("com.android.library") version "8.4.1" apply false
  id("com.google.devtools.ksp") version "1.9.23-1.0.20" apply false
  id("com.google.gms.google-services") version "4.4.0" apply false
  id("com.google.firebase.crashlytics") version "2.9.9" apply false
  id("androidx.navigation.safeargs") version "2.7.2" apply false
  id("com.google.dagger.hilt.android") version "2.51" apply false
  kotlin("plugin.serialization") version "1.9.23" apply false
}