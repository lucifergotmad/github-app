package com.lucifergotmad.githubapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.lucifergotmad.githubapp.data.local.room.UserDao
import com.lucifergotmad.githubapp.data.remote.retrofit.UserService
import com.lucifergotmad.githubapp.domain.User

class UserRepository private constructor(
    private val userService: UserService,
    private val mUserDao: UserDao
) {

    fun getUsers(): LiveData<Result<List<User>>> = liveData {
        emit(Result.Loading)
        try {
            val response = userService.findUsers(perPage = 30)
            val listUsers = response.map {
                User(it.login, it.avatarUrl, it.htmlUrl)
            }

            emit(Result.Success(listUsers))
            Log.v("UserRepository", "getUsers: $response")
        } catch (e: Exception) {
            Log.d("UserRepository", "getUsers: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userService: UserService,
            userDao: UserDao
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userService, userDao)
            }.also { instance = it }
    }
}