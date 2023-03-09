package com.lucifergotmad.githubapp.data.remote.retrofit

import androidx.viewbinding.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getUserService(): UserService {
            val loggingInterceptor =
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                } else {
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
                }
            val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor)
                .addInterceptor(
                    OAuthInterceptor(
                        "Bearer",
                        "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"
                    )
                ).build()
            val retrofit = Retrofit.Builder().baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create()).client(client).build()

            return retrofit.create(UserService::class.java)
        }
    }
}