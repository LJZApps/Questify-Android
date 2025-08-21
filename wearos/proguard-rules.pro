# Add project specific ProGuard rules here.
# For more details, see http://developer.android.com/guide/developing/tools/proguard.html

# --- Wichtige allgemeine Kotlin-Regeln ---
# Behält Metadaten, die für Reflection und andere Kotlin-Features wichtig sind.
-keep,allowobfuscation,allowshrinking class kotlin.Metadata { *; }
-keep class kotlin.coroutines.** { *; }
-dontwarn kotlin.collections.jdk8.*

# --- Kotlinx Serialization ---
# Verhindert, dass Serializer-Klassen entfernt werden.
-keep class kotlinx.serialization.** { *; }
-keepclassmembers class ** {
    @kotlinx.serialization.Serializable <methods>;
}
-keep class *$$serializer { *; }

# --- Ktor Client (Offizielle und erweiterte Regeln) ---
# Diese Regeln sind entscheidend für Ktor, insbesondere mit der CIO-Engine.
-keep class io.ktor.** { *; }
-keepclassmembers class io.ktor.** { *; }
-keepnames class io.ktor.** { *; }

# Wichtig für Coroutines und die asynchrone Verarbeitung in Ktor
-keep class kotlinx.coroutines.** { *; }
-keepclassmembers class kotlinx.coroutines.** { *; }
-keepnames class kotlinx.coroutines.** { *; }

# Behält Klassen, die zur Laufzeit für die Ktor-Engine benötigt werden.
-keepattributes Signature
-keepnames class kotlinx.coroutines.internal.**
-keepnames class kotlinx.coroutines.selects.**
-keepclassmembers class kotlinx.coroutines.flow.internal.** {
    *;
}

# --- OkHttp & Okio ---
# Standardregeln für OkHttp und seine Abhängigkeit Okio.
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-keep class okio.** { *; }
-keep interface okio.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**

# --- Suppress Warnings (kann zur Sicherheit bleiben) ---
# Diese Regeln verhindern Build-Warnungen, auch wenn die Klassen durch die obigen Regeln schon behalten werden.
-dontwarn io.ktor.**
-dontwarn org.slf4j.**
