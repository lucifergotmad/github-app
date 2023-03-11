package com.lucifergotmad.githubapp.ui.favorite

import androidx.lifecycle.ViewModel
import com.lucifergotmad.githubapp.data.UserRepository

class FavoriteUserViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getFavoriteUsers() = userRepository.getFavoriteUsers()
}