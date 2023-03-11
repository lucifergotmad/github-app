package com.lucifergotmad.githubapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.lucifergotmad.githubapp.data.local.entity.UserEntity
import com.lucifergotmad.githubapp.data.local.room.UserDao
import com.lucifergotmad.githubapp.data.remote.retrofit.UserService
import com.lucifergotmad.githubapp.domain.DetailUser
import com.lucifergotmad.githubapp.domain.User

class UserRepository private constructor(
    private val userService: UserService,
    private val mUserDao: UserDao
) {
    fun getFavoriteUsers(): LiveData<List<UserEntity>> = mUserDao.getUsers()

    suspend fun isUserFavorite(username: String): Boolean = mUserDao.isFavoriteUser(username)

    suspend fun addToFavorite(user: UserEntity) = mUserDao.insertUser(user)

    suspend fun removeFromFavorite(username: String) = mUserDao.deleteUser(username)


    fun getUsers(): LiveData<Result<List<User>>> = liveData {
        emit(Result.Loading)
        try {
            val response = userService.findUsers(perPage = 20)
            val listUsers = response.map {
                User(it.login, it.avatarUrl, it.htmlUrl)
            }

            emit(Result.Success(listUsers))
        } catch (e: Exception) {
            Log.d("UserRepository", "getUsers: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun searchUser(q: String?): LiveData<Result<List<User>>> = liveData {
        emit(Result.Loading)
        try {
            val response = userService.searchUser(q)
            val listUsers = response.items.map {
                User(it.login, it.avatarUrl, it.htmlUrl)
            }

            emit(Result.Success(listUsers))
        } catch (e: Exception) {
            Log.d("UserRepository", "searchUser: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getUserByUsername(username: String): LiveData<Result<DetailUser>> = liveData {
        emit(Result.Loading)
        try {
            val response = userService.findUserByUsername(username)
            val detailUser = DetailUser(
                username = response.login,
                avatarUrl = response.avatarUrl,
                company = response.company,
                fullName = response.name,
                blog = response.blog,
                location = response.location,
                email = response.email,
                bio = response.bio,
                twitterUsername = response.twitterUsername,
                publicRepos = response.publicRepos,
                followers = response.followers,
                following = response.following,
                hireAble = response.hireAble,
                githubUrl = response.htmlUrl
            )

            emit(Result.Success(detailUser))
        } catch (e: Exception) {
            Log.d("UserRepository", "getUserByUsername: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getUserFollower(username: String): LiveData<Result<List<User>>> = liveData {
        emit(Result.Loading)
        try {
            val response = userService.getFollowers(username)
            val listUsers = response.map {
                User(it.login, it.avatarUrl, it.htmlUrl)
            }

            emit(Result.Success(listUsers))
            Log.v("UserRepository", "getUserFollower: $response")
        } catch (e: Exception) {
            Log.d("UserRepository", "getUserFollower: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getUserFollowing(username: String): LiveData<Result<List<User>>> = liveData {
        emit(Result.Loading)
        try {
            val response = userService.getFollowing(username)
            val listUsers = response.map {
                User(it.login, it.avatarUrl, it.htmlUrl)
            }

            emit(Result.Success(listUsers))
            Log.v("UserRepository", "getUserFollowing: $response")
        } catch (e: Exception) {
            Log.d("UserRepository", "getUserFollowing: ${e.message.toString()} ")
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