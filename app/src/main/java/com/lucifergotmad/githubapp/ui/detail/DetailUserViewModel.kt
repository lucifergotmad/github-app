package com.lucifergotmad.githubapp.ui.detail

import androidx.lifecycle.ViewModel
import com.lucifergotmad.githubapp.data.UserRepository

class DetailUserViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getUserByUsername(username: String) = userRepository.getUserByUsername(username)
}