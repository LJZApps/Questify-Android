package de.ljz.questify.core.main

import androidx.activity.ComponentActivity
import com.google.android.gms.wearable.Wearable

class ActivityMain : ComponentActivity() {
    private val dataClient by lazy { Wearable.getDataClient(this) }
}