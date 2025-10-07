package de.ljz.questify

import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import com.kavi.droid.color.palette.KvColorPalette
import com.posthog.android.PostHogAndroid
import com.posthog.android.PostHogAndroidConfig
import dagger.hilt.android.HiltAndroidApp
import de.ljz.questify.core.utils.Standard
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()

        KvColorPalette.initialize(baseColor = Color.Standard)

        val config = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

        WorkManager.Companion.initialize(this, config)

        val postHogConfig = PostHogAndroidConfig(
            apiKey = BuildConfig.POSTHOG_API_KEY,
            host = BuildConfig.POSTHOG_HOST
        )

        PostHogAndroid.setup(this, postHogConfig)
    }
}