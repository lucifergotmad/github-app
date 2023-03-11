package com.lucifergotmad.githubapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucifergotmad.githubapp.data.UserRepository
import com.lucifergotmad.githubapp.domain.User

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getUsers() = userRepository.getUsers()

    fun searchUsers(q: String?) = userRepository.searchUser(q)
}