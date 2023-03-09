package com.lucifergotmad.githubapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lucifergotmad.githubapp.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY id DESC")
    fun getUsers(): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserEntity)

    @Query("DELETE FROM users WHERE username = :username")
    suspend fun deleteUser(username: String)

    @Query("SELECT EXISTS(SELECT * FROM news WHERE username = :username)")
    suspend fun isFavoriteUser(title: String): Boolean
}