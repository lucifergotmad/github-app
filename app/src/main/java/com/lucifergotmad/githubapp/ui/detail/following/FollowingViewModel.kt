package com.lucifergotmad.githubapp.ui.detail.following

import androidx.lifecycle.ViewModel
import com.lucifergotmad.githubapp.data.UserRepository

class FollowingViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getUserFollowing(username: String) = userRepository.getUserFollowing(username)

}