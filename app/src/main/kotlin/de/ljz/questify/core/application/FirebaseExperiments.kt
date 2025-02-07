package de.ljz.questify.core.application

object FirebaseExperiments {
    sealed class Feature(val featureName: String) {
        companion object {
            const val ENABLED = true
            const val DISABLED = false
        }

        val enabled get() = ENABLED
        val disabled get() = DISABLED
    }

    object Features {
        object Dailies : Feature("feature_dailies_enabled")
    }
}