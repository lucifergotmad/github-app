package com.lucifergotmad.githubapp.data.remote.retrofit

import com.lucifergotmad.githubapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfig {
    companion object {
        fun getUserService(): UserService {
            val loggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }

            val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).addInterceptor(
                OAuthInterceptor(
                    "Bearer", BuildConfig.API_KEY
                )
            ).readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS).build()

            val retrofit = Retrofit.Builder().baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create()).client(client).build()

            return retrofit.create(UserService::class.java)
        }
    }
}