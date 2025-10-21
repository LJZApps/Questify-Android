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
    }
}

dependencies {
    // Standard-Bibliotheken
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)

    // --- HIER IST DIE MAGIE ---
    // 1. Die Compose BOM (Bill of Materials) einbinden
    // Diese sorgt dafür, dass alle folgenden Compose-Bibliotheken
    // zueinander passende Versionen haben. Super wichtig!
    implementation(platform(libs.androidx.compose.bom))

    // 2. Die absolut notwendigen Compose-Bibliotheken
    implementation(libs.androidx.runtime) // Die Kern-Laufzeitumgebung
    implementation(libs.androidx.ui)      // Die Basis für alle UI-Elemente

    // 3. Die speziellen Bibliotheken für Wear OS Compose
    implementation(libs.androidx.wear.compose.foundation)
    implementation(libs.androidx.wear.compose.material)
    implementation(libs.androidx.wear.compose.navigation)

    // 4. Für die Vorschau in Android Studio
    implementation(libs.ui.tooling.preview)
    debugImplementation(libs.androidx.wear.compose.tooling)

    // Für die Kommunikation mit dem Handy
    implementation(libs.play.services.wearable)
}
