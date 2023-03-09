package com.lucifergotmad.githubapp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lucifergotmad.githubapp.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun mUserDao(): UserDao

    companion object {
        @Volatile
        private var instance: GithubDatabase? = null
        fun getInstance(context: Context): GithubDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    GithubDatabase::class.java, "github_db"
                ).build()
            }
    }
}