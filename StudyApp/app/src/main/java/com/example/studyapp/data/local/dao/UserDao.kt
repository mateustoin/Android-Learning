package com.example.studyapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.studyapp.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY name ASC")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

//    @Update
//    suspend fun updateUser(user: UserEntity)

//    @Delete
//    suspend fun deleteUser(user: UserEntity)
    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteUser(userId: Int)
}
