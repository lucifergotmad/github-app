package com.lucifergotmad.githubapp.data.remote.retrofit

import com.lucifergotmad.githubapp.data.remote.response.DetailUserResponse
import com.lucifergotmad.githubapp.data.remote.response.SearchUserResponse
import com.lucifergotmad.githubapp.data.remote.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @GET("users")
    suspend fun findUsers(@Query("per_page") perPage: Int?): ArrayList<UserResponse>

    @GET("users/{username}")
    suspend fun findUserByUsername(@Path("username") username: String?): DetailUserResponse

    @GET("users/{username}/followers")
    suspend fun getFollowers(@Path("username") username: String?): ArrayList<UserResponse>

    @GET("users/{username}/following")
    suspend fun getFollowing(@Path("username") username: String?): ArrayList<UserResponse>

    @GET("search/users")
    suspend fun searchUser(
        @Query("q") q: String?,
        @Query("order") order: String?,
        @Query("sort") sort: String?
    ): SearchUserResponse
}