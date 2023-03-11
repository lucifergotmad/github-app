package com.lucifergotmad.githubapp.helper

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lucifergotmad.githubapp.data.UserRepository
import com.lucifergotmad.githubapp.di.Injection
import com.lucifergotmad.githubapp.ui.detail.DetailUserViewModel
import com.lucifergotmad.githubapp.ui.detail.follower.FollowerViewModel
import com.lucifergotmad.githubapp.ui.detail.following.FollowingViewModel
import com.lucifergotmad.githubapp.ui.favorite.FavoriteUserViewModel
import com.lucifergotmad.githubapp.ui.home.HomeViewModel
import com.lucifergotmad.githubapp.ui.settings.SettingPreferences
import com.lucifergotmad.githubapp.ui.settings.SettingViewModel

class ViewModelFactory private constructor(
    private val userRepository: UserRepository,
) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(DetailUserViewModel::class.java)) {
            return DetailUserViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(FollowerViewModel::class.java)) {
            return FollowerViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(FollowingViewModel::class.java)) {
            return FollowingViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(FavoriteUserViewModel::class.java)) {
            return FavoriteUserViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideRepository(context),
                )
            }.also { instance = it }
    }
}