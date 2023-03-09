package com.lucifergotmad.githubapp.di

import android.content.Context
import com.lucifergotmad.githubapp.data.UserRepository
import com.lucifergotmad.githubapp.data.local.room.GithubDatabase
import com.lucifergotmad.githubapp.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val userService = ApiConfig.getUserService()
        val database = GithubDatabase.getInstance(context)
        val dao = database.mUserDao()
        return UserRepository.getInstance(userService, dao)
    }
}