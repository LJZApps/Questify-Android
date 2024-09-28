import java.io.FileInputStream
import java.util.Properties

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.jetbrains.kotlin.android)
  alias(libs.plugins.jetbrainsCompose)
  alias(libs.plugins.compose.compiler)

  id("com.google.devtools.ksp")
  id("com.google.firebase.crashlytics")
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

  compileSdk = 35

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
    targetSdk = 35
    versionCode = 1
    versionName = "0.1"
    resourceConfigurations += listOf("en", "de")
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_19
    targetCompatibility = JavaVersion.VERSION_19
  }

  buildTypes {
    getByName("release") {
      signingConfig = signingConfigs.getByName("debug")
    }
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
  implementation(libs.androidx.datastore.core.android)
  ksp(libs.androidx.room.compiler)

  ksp("io.github.raamcosta.compose-destinations:ksp:$composeDestinationsVersion")

  implementation(libs.androidx.datastore.preferences.core)

  implementation(project.dependencies.platform("androidx.compose:compose-bom:2024.05.00"))
  implementation(project.dependencies.platform("com.google.firebase:firebase-bom:33.0.0"))

  implementation(platform(libs.arrow.stack))
  implementation(platform(libs.sentry.bom))

  implementation(libs.accompanist.swiperefresh)
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.activity.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.androidx.constraintlayout.compose)
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.hilt.navigation.compose)
  implementation(libs.androidx.lifecycle.runtime.compose)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.lifecycle.viewmodel.compose)
  implementation(libs.androidx.navigation.compose)
  implementation(libs.androidx.room.guava)
  implementation(libs.androidx.room.ktx)
  implementation(libs.androidx.room.paging)
  implementation(libs.androidx.room.runtime)
  implementation(libs.androidx.room.rxjava2)
  implementation(libs.androidx.room.rxjava3)
  implementation(libs.arrow.core)
  implementation(libs.billing.ktx)
  implementation(libs.compose.destinations.core)
  implementation(libs.composeSettings.ui)
  implementation(libs.composeSettings.ui.extended)
  implementation(libs.converter.moshi)
  implementation(libs.firebase.analytics)
  implementation(libs.firebase.crashlytics)
  implementation(libs.gson)
  implementation(libs.hilt.android)
  implementation(libs.kotlin.stdlib.jdk8)
  implementation(libs.kotlinx.collections.immutable)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.logging.interceptor)
  implementation(libs.material)
  implementation(libs.material3)
  implementation(libs.material3.window.size)
  implementation(libs.moshi.adapters)
  implementation(libs.moshi.kotlin)
  implementation(libs.okhttp)
  implementation(libs.retrofit)
  implementation(libs.sandwich)
  implementation(libs.sandwich.retrofit)
  implementation(libs.sandwich.retrofit.serialization)
  implementation(libs.sentry.android)
  implementation(libs.sentry.compose.android)
  implementation(libs.ui.graphics)
  implementation(libs.ui.tooling.preview)
  implementation(libs.voyager.navigator)
  implementation(libs.voyager.transitions)
  implementation(libs.voyager.screenModel)
  implementation(libs.voyager.hilt)
  implementation(libs.voyager.tabNavigator)
  implementation(libs.voyager.bottomSheetNavigator)
  implementation(libs.voyager.kodein)
  implementation(libs.voyager.rxjava)

  implementation("androidx.compose.material:material-icons-extended:$composeVersion")
  implementation("androidx.compose.ui:ui-tooling:$composeVersion")
  implementation("androidx.compose.ui:ui:$composeVersion")
  implementation("io.ktor:ktor-client-cio:$ktorVersion")
  implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
  implementation("io.ktor:ktor-client-core:$ktorVersion")
  implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

  kapt(libs.androidx.hilt.compiler)
  kapt(libs.hilt.android.compiler)
  annotationProcessor("androidx.room:room-compiler:$roomVersion")
}


sentry {
  org.set("ljz-apps")
  projectName.set("questify")

  // this will upload your source code to Sentry to show it as part of the stack traces
  // disable if you don't want to expose your sources
  includeSourceContext.set(true)
}
