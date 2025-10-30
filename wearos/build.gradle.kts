import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    // Wichtig: Stellt sicher, dass der Compose Compiler aktiv ist
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "de.ljz.questify"
    compileSdk = 36

    defaultConfig {
        applicationId = "de.ljz.questify"
        minSdk = 30
        targetSdk = 36
        versionCode = 19
        versionName = "0.1"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.horologist.compose.layout)
    implementation(libs.horologist.compose.material)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.runtime)
    implementation(libs.androidx.ui)

    implementation(libs.androidx.wear.compose.foundation)
    implementation(libs.androidx.wear.compose.material)
    implementation(libs.androidx.wear.compose.navigation)

    implementation(libs.ui.tooling.preview)
    debugImplementation(libs.androidx.wear.compose.tooling)

    implementation(libs.play.services.wearable)
}
