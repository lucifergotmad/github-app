package com.lucifergotmad.githubapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucifergotmad.githubapp.data.UserRepository
import com.lucifergotmad.githubapp.data.local.entity.UserEntity
import kotlinx.coroutines.launch

class DetailUserViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getUserByUsername(username: String) = userRepository.getUserByUsername(username)

    fun isUserFavorite(username: String): LiveData<Boolean> {
        val isFavorite = MutableLiveData<Boolean>()
        viewModelScope.launch {
            isFavorite.postValue(userRepository.isUserFavorite(username))
        }
        return isFavorite
    }

    fun addToFavorite(user: UserEntity) {
        viewModelScope.launch {
            userRepository.addToFavorite(user)
        }
    }

    fun removeFromFavorite(username: String) {
        viewModelScope.launch {
            userRepository.removeFromFavorite(username)
        }
    }
}