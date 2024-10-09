package de.ljz.questify.core.di

import com.skydoves.sandwich.retrofit.adapters.ApiResponseCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.ljz.questify.BuildConfig
import de.ljz.questify.data.api.core.ApiClient
import de.ljz.questify.data.api.core.adapters.StringToDateAdapter
import de.ljz.questify.data.api.core.interceptors.FailedRequestInterceptor
import de.ljz.questify.data.sharedpreferences.SessionManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.Duration

val networkModule = module {
  single<SessionManager> { SessionManager(androidContext()) }

  single<Moshi> {
    Moshi.Builder()
      .add(StringToDateAdapter())
      .add(KotlinJsonAdapterFactory())
      .build()
  }

  single<Retrofit> {
    Retrofit.Builder()
      .baseUrl(BuildConfig.BASE_URL)
      .client(get())
      .addConverterFactory(MoshiConverterFactory.create(get()))
      .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
      .build()
  }

  single<OkHttpClient> {
    OkHttpClient.Builder().apply {
      callTimeout(Duration.ofMinutes(3))
      connectTimeout(Duration.ofMinutes(3))
      readTimeout(Duration.ofMinutes(3))
      writeTimeout(Duration.ofMinutes(3))

      addInterceptor(FailedRequestInterceptor(get()))

      if (BuildConfig.DEBUG) {
        addNetworkInterceptor(
          HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
          }
        )
      }
    }.build()
  }

  single<ApiClient> {
    ApiClient(
      retrofit = get(),
    )
  }
}