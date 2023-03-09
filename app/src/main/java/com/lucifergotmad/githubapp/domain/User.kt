package com.lucifergotmad.githubapp.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(val username: String, val avatarUrl: String, val githubUrl: String) : Parcelable