package com.lucifergotmad.githubapp.ui.detail.follower

import androidx.lifecycle.ViewModel
import com.lucifergotmad.githubapp.data.UserRepository

class FollowerViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getUserFollower(username: String) = userRepository.getUserFollower(username)

}