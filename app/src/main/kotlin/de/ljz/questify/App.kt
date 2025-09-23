package de.ljz.questify

import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import com.kavi.droid.color.palette.KvColorPalette
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
    }
}