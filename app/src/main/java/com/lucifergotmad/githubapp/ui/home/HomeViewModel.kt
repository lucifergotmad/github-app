package com.lucifergotmad.githubapp.ui.home

import androidx.lifecycle.ViewModel
import com.lucifergotmad.githubapp.data.UserRepository

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getUsers() = userRepository.getUsers()
}