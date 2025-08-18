import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)

    id("com.google.devtools.ksp")
//    id("com.google.firebase.crashlytics")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.gms.google-services")
    id("kotlinx-serialization")
    kotlin("plugin.serialization")
    id("com.google.dagger.hilt.android")

    id("androidx.room")

    id("io.sentry.android.gradle") version "4.14.1"
}

room {
    schemaDirectory("$projectDir/schemas")
}

android {
    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localProperties.load(FileInputStream(localPropertiesFile))
    }

    androidResources {
        localeFilters += listOf("en", "de")
    }

    compileSdk = 36

    buildFeatures {
        compose = true
        buildConfig = true
    }

    kotlinOptions {
        jvmTarget = "19"
    }

    defaultConfig {
        namespace = "de.ljz.questify"
        applicationId = "de.ljz.questify"
        minSdk = 30
        targetSdk = 36
        versionCode = 15
        versionName = "0.8.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }

    signingConfigs {
        create("release") {
            storeFile = file(localProperties.getProperty("STORE_FILE"))
            storePassword = localProperties.getProperty("STORE_PASSWORD")
            keyAlias = localProperties.getProperty("KEY_ALIAS")
            keyPassword = localProperties.getProperty("KEY_PASSWORD")
        }
    }

    buildTypes {
        named("debug") {
            isDebuggable = true
            applicationIdSuffix = ".DEV"
            versionNameSuffix = "-DEV"

            buildConfigField(
                "String",
                "BASE_URL",
                "\"${localProperties.getProperty("DEBUG_BASE_URL")}\""
            )
        }

        named("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            buildConfigField(
                "String",
                "BASE_URL",
                "\"${localProperties.getProperty("RELEASE_BASE_URL")}\""
            )
        }
    }
}

val roomVersion by extra("2.6.1")
val ktorVersion by extra("2.2.1")

dependencies {
    // FONT
    implementation(libs.androidx.ui.text.google.fonts)

    // Adaptive
    // Haupt-Library für adaptive Komponenten
    implementation(libs.androidx.adaptive)

    // Speziell für adaptive Layouts
    implementation(libs.androidx.adaptive.layout)

    // Für die adaptive Navigation
    implementation(libs.androidx.adaptive.navigation)

    implementation(libs.androidx.material3.adaptive.navigation.suite)

    // If you're using Material 3, use compose-placeholder-material3
    implementation(libs.compose.placeholder.material3)

    // Coil
    implementation(libs.coil.compose)

    // Compose Color Picker
    implementation(libs.compose.colorpicker)

    // Dynamic Material You
    implementation(libs.material.kolor)

    // Gampose https://github.com/ezlifeSol/gampose
    //implementation(libs.gampose)

    // Yaml
    implementation(libs.jackson.dataformat.yaml)
    implementation(libs.jackson.module.kotlin)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    ksp(libs.androidx.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.hilt.work)

    ksp(libs.androidx.room.compiler)

    implementation(project.dependencies.platform("androidx.compose:compose-bom-alpha:2025.08.00"))
//    implementation(project.dependencies.platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation(libs.androidx.runtime)

    implementation(platform(libs.sentry.bom))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.datastore.core.android)
    implementation(libs.androidx.datastore.preferences.core)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.room.guava)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.rxjava2)
    implementation(libs.androidx.room.rxjava3)
    implementation(libs.composeSettings.ui)
    implementation(libs.composeSettings.ui.extended)
    implementation(libs.converter.moshi)
//    implementation(libs.firebase.analytics)
//    implementation(libs.firebase.crashlytics)
    implementation(libs.gson)
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
    implementation(libs.sandwich.ktor)
    implementation(libs.sentry.android)
    implementation(libs.sentry.compose.android)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.kotlin.metadata.jvm)

    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui)
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

    implementation("io.ktor:ktor-client-logging:$ktorVersion")
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")

    annotationProcessor("androidx.room:room-compiler:$roomVersion")
}

sentry {
    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localProperties.load(FileInputStream(localPropertiesFile))
    }

    org.set("ljz-apps")
    projectName.set("questify")

    authToken.set(localProperties.getProperty("SENTRY_AUTH_TOKEN"))

    // this will upload your source code to Sentry to show it as part of the stack traces
    // disable if you don't want to expose your sources
    includeSourceContext.set(true)
}
