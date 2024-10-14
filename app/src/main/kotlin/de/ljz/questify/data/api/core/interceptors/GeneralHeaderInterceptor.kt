package de.ljz.questify.data.api.core.interceptors

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import de.ljz.questify.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale

class GeneralHeaderInterceptor(private val context: Context) : Interceptor {
    @SuppressLint("HardwareIds")
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request().newBuilder().apply {
            addHeader("Accept-Language", Locale.getDefault().toString())
            addHeader("X-APPLICATION-ID", 1.toString())
            addHeader("X-APPLICATION-VERSION", BuildConfig.VERSION_NAME)
            addHeader("X-APPLICATION-BUILD", BuildConfig.VERSION_CODE.toString())
            addHeader(
                "X-DEVICE-ID",
                Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            )
            addHeader("X-DEVICE-MANUFACTURER", Build.MANUFACTURER)
            addHeader("X-DEVICE-MODEL", Build.MODEL)
            addHeader("X-OPERATING-SYSTEM", "Android")
            addHeader("X-OPERATING-SYSTEM-VERSION", Build.VERSION.SDK_INT.toString())
            addHeader("X-DEBUG-BUILD", (if (BuildConfig.DEBUG) 1 else 0).toString())
            addHeader("Upgrade", "h2c")
        }.let {
            chain.proceed(it.build())
        }
    }
}