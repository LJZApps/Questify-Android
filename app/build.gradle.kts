import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)

    id("com.google.firebase.crashlytics")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
    id("kotlinx-serialization")
    kotlin("plugin.serialization")
    id("com.google.dagger.hilt.android")

    id("androidx.room")

    id("io.sentry.android.gradle") version "4.11.0"
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

    defaultConfig {
        namespace = "de.ljz.questify"
        applicationId = "de.ljz.questify"
        minSdk = 26
        targetSdk = 35
        versionCode = 3
        versionName = "0.3"
        resourceConfigurations += listOf("en", "de")
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
        getByName("release") {
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

        named("debug") {
            isDebuggable = true

            buildConfigField(
                "String",
                "BASE_URL",
                "\"${localProperties.getProperty("DEBUG_BASE_URL")}\""
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
        }
    }
    kapt {
        correctErrorTypes = true
    }
}

val roomVersion by extra("2.6.1")
val composeVersion by extra("1.7.4")
val ktorVersion by extra("2.2.1")

dependencies {
    // Texty
    implementation(libs.texty.android)

    // Konfetti
    implementation(libs.konfetti.compose)

    // Balloon (tooltips)
    implementation(libs.balloon.compose)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    kapt(libs.androidx.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.androidx.room.compiler)

    implementation(project.dependencies.platform("androidx.compose:compose-bom:2024.09.03"))
    implementation(project.dependencies.platform("com.google.firebase:firebase-bom:33.4.0"))

    implementation(platform(libs.sentry.bom))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.datastore.core.android)
    implementation(libs.androidx.datastore.preferences.core)
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
    implementation(libs.composeSettings.ui)
    implementation(libs.composeSettings.ui.extended)
    implementation(libs.converter.moshi)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
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

    implementation("androidx.compose.material:material-icons-extended:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.ui:ui:$composeVersion")
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
