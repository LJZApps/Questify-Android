import java.io.FileInputStream
import java.util.Properties

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.jetbrains.kotlin.android)

  id("com.google.devtools.ksp")
  id("com.google.firebase.crashlytics")
  id("kotlin-android")
  id("kotlin-parcelize")
  id("androidx.navigation.safeargs.kotlin")
  id("kotlin-kapt")
  id("com.google.gms.google-services")
  id("com.google.dagger.hilt.android")
  id("kotlinx-serialization")
  kotlin("plugin.serialization")

  id("io.sentry.android.gradle") version "4.5.1"
}

android {
  val properties = Properties().apply {
    load(FileInputStream(File(rootProject.rootDir, "app.properties")))
  }

  compileSdk = 34

  buildFeatures {
    viewBinding = true
    compose = true
    buildConfig = true
  }

  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.13"
  }

  kotlinOptions {
    jvmTarget = "19"
  }

  ksp {
    arg("compose-destinations.codeGenPackageName", "de.ljz.questify.ui.navigation")
  }

  defaultConfig {
    namespace = "de.ljz.questify"
    applicationId = "de.ljz.questify"
    minSdk = 26
    targetSdk = 34
    versionCode = 1
    versionName = "0.1"
    resourceConfigurations += listOf("en", "de")
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_19
    targetCompatibility = JavaVersion.VERSION_19
  }

  buildTypes {
    named("debug") {
      isDebuggable = true

      buildConfigField(
        "String",
        "BASE_URL",
        "\"${properties.getProperty("DEBUG_BASE_URL")}\""
      )
    }

    named("release") {
      isDebuggable = false
      isMinifyEnabled = false
      setProguardFiles(
        listOf(
          getDefaultProguardFile("proguard-android-optimize.txt"),
          "proguard-rules.pro"
        )
      )

      buildConfigField(
        "String",
        "BASE_URL",
        "\"${properties.getProperty("RELEASE_BASE_URL")}\""
      )
    }
  }
  kapt {
    correctErrorTypes = true
  }
}

val okHttpVersion by extra("4.12.0")
val roomVersion by extra("2.6.1")
val composeVersion by extra("1.6.5")
val composeDestinationsVersion by extra("1.10.2")
val ktorVersion by extra("2.2.1")

dependencies {
  // Compose settings
  implementation(libs.composeSettings.ui.extended)
  implementation(libs.composeSettings.ui)

  // Sandwich
  implementation(libs.sandwich)
  implementation(libs.sandwich.retrofit) // For Retrofit (Android)
  implementation(libs.sandwich.retrofit.serialization)

  // Sentry
  implementation(platform(libs.sentry.bom)) //import bom
  implementation(libs.sentry.android) //no version specified
  implementation(libs.sentry.compose.android)

  // KotlinX Serialization
  implementation(libs.kotlinx.collections.immutable)
  implementation(libs.kotlinx.serialization.json)


  // Compose destinations
  ksp("io.github.raamcosta.compose-destinations:ksp:$composeDestinationsVersion")
  implementation(libs.compose.destinations.core)

  // Arrow
  implementation(platform(libs.arrow.stack))
  implementation(libs.arrow.core)

  // For AppWidgets support
  implementation(libs.androidx.glance.appwidget)

  // For interop APIs with Material 3
  implementation(libs.androidx.glance.material3)

  // OkHttp
  implementation(libs.okhttp)
  implementation(libs.logging.interceptor)

  // Dagger & Hilt
  kapt(libs.hilt.android.compiler)
  kapt(libs.androidx.hilt.compiler)
  implementation(libs.hilt.android)
  implementation(libs.androidx.hilt.navigation.compose)

  // Moshi
  implementation(libs.moshi.kotlin)
  implementation(libs.moshi.adapters)

  // Retrofit
  implementation(libs.retrofit)
  implementation(libs.converter.moshi)

  // Ktor Client
  implementation("io.ktor:ktor-client-core:$ktorVersion")
  implementation("io.ktor:ktor-client-cio:$ktorVersion")
  implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
  implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

  // Room
  ksp(libs.androidx.room.compiler)
  implementation(libs.androidx.room.rxjava2)
  implementation(libs.androidx.room.rxjava3)
  implementation(libs.androidx.room.guava)
  implementation(libs.androidx.room.paging)
  implementation(libs.androidx.room.runtime)
  annotationProcessor("androidx.room:room-compiler:$roomVersion")

  // Jetpack Compose
  implementation(project.dependencies.platform("androidx.compose:compose-bom:2024.05.00"))
  implementation(libs.androidx.navigation.compose)
  implementation(libs.ui.graphics)
  implementation(libs.ui.tooling.preview)
  implementation("androidx.compose.ui:ui:$composeVersion")
  implementation("androidx.compose.ui:ui-tooling:$composeVersion")
  implementation("androidx.compose.material:material-icons-extended:$composeVersion")
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.constraintlayout.compose)

  // Google extensions
  implementation(libs.gson)
  implementation(libs.accompanist.swiperefresh)

  // Material
  implementation(libs.material)
  implementation(libs.material3)
  implementation(libs.material3.window.size)

  // androidx
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.androidx.activity.ktx)

  // Lifecycle
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.lifecycle.viewmodel.compose)
  implementation(libs.androidx.lifecycle.runtime.compose)

  // Firebase
  implementation(libs.firebase.analytics)
  implementation(libs.firebase.crashlytics)
  implementation(project.dependencies.platform("com.google.firebase:firebase-bom:33.0.0"))

  // Other
  implementation(libs.kotlin.stdlib.jdk8)
  implementation(libs.billing.ktx)
}

sentry {
    org.set("ljz-apps")
    projectName.set("questify")

    // this will upload your source code to Sentry to show it as part of the stack traces
    // disable if you don't want to expose your sources
    includeSourceContext.set(true)
}
