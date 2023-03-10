package com.lucifergotmad.githubapp.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailUser(
    val username: String,
    val avatarUrl: String,
    val fullName: String,
    val company: String,
    val blog: String,
    val location: String,
    val email: String,
    val bio: String?,
    val twitterUsername: String,
    val publicRepos: Int,
    val followers: Int,
    val following: Int,
    val hireAble: Boolean
) : Parcelable