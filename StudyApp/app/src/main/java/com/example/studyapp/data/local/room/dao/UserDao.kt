package com.example.studyapp.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.studyapp.data.local.room.entity.UserEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY name ASC")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()

    @Transaction
    suspend fun clearAndInsertUsers(users: List<UserEntity>) {
        deleteAllUsers()
        insertUsers(users)
    }

    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteUser(userId: Long)

    @Query("SELECT * FROM users WHERE isSynced = 0")
    suspend fun getUnsyncedUsers(): List<UserEntity>

    @Query("UPDATE users SET isSynced = 1 WHERE id = :userId")
    suspend fun markAsSynced(userId: Long)
}
