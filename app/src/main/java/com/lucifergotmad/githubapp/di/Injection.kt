package com.lucifergotmad.githubapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.lucifergotmad.githubapp.data.UserRepository
import com.lucifergotmad.githubapp.data.local.room.GithubDatabase
import com.lucifergotmad.githubapp.data.remote.retrofit.ApiConfig
import com.lucifergotmad.githubapp.ui.settings.SettingPreferences

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val userService = ApiConfig.getUserService()
        val database = GithubDatabase.getInstance(context)
        val dao = database.mUserDao()
        return UserRepository.getInstance(userService, dao)
    }
}