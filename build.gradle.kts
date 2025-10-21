// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.compose.compiler) apply false

    id("com.android.library") version "8.13.0" apply false
    id("com.google.gms.google-services") version "4.4.4" apply false
//    id("com.google.firebase.crashlytics") version "3.0.2" apply false
    id("androidx.navigation.safeargs") version "2.9.5" apply false
    kotlin("plugin.serialization") version "2.1.0" apply false
    id("com.google.dagger.hilt.android") version "2.57.2" apply false
    id("com.google.devtools.ksp") version "2.2.20-2.0.3" apply false
    id("androidx.room") version "2.8.2" apply false
}