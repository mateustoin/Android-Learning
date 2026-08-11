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
    @Query("SELECT * FROM users WHERE isDeleted = 0 ORDER BY name ASC")
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

    @Query("UPDATE users SET isDeleted = 1, isSynced = 0 WHERE id = :userId")
    suspend fun markForDeletion(userId: Long)

    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteUserPermanently(userId: Long)

    @Query("SELECT * FROM users WHERE isSynced = 0 AND isDeleted = 0")
    suspend fun getPendingSyncUsers(): List<UserEntity>

    @Query("SELECT * FROM users WHERE isSynced = 0 AND isDeleted = 1")
    suspend fun getPendingDeletions(): List<UserEntity>

    @Query("UPDATE users SET isSynced = 1, remoteId = :remoteId WHERE id = :localId")
    suspend fun markAsSynced(localId: Long, remoteId: String)
}
