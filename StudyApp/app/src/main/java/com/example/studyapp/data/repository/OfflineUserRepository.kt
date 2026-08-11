package com.example.studyapp.data.repository

import android.util.Log
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.studyapp.data.local.room.dao.UserDao
import com.example.studyapp.data.mapper.toEntity
import com.example.studyapp.data.mapper.toUser
import com.example.studyapp.data.remote.api.UserApiService
import com.example.studyapp.data.remote.sync.SyncWorker
import com.example.studyapp.domain.model.User
import com.example.studyapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val TAG = "OfflineUserRepository"

class OfflineUserRepository @Inject constructor(
    private val userDao: UserDao,
    private val apiService: UserApiService,
    private val workManager: WorkManager
): UserRepository {

    override suspend fun getUsers(): Flow<List<User>> {
        return userDao.getAllUsers().map { entities ->
            entities.map { it.toUser() }
        }
    }

    override suspend fun addUser(user: User) {
        try {
            Log.d(TAG, "Adding user locally: ${user.name}")
            // Local-first: Save to Room with isSynced = false
            userDao.insertUser(user.toEntity().copy(isSynced = false))
            scheduleSync()
        } catch (e: Exception) {
            Log.e(TAG, "Error adding user locally: ${e.message}", e)
        }
    }

    override suspend fun deleteUser(userId: Long) {
        try {
            Log.d(TAG, "Marking user for deletion: $userId")
            // Local-first: Mark for deletion
            userDao.markForDeletion(userId)
            scheduleSync()
        } catch (e: Exception) {
            Log.e(TAG, "Error marking user for deletion: ${e.message}", e)
        }
    }

    override suspend fun refreshUsers() {
        try {
            Log.d(TAG, "Refreshing users from remote...")
            val remoteUsers = apiService.getAllUsers()
            Log.d(TAG, "Fetched ${remoteUsers.size} users from remote")
            
            val entities = remoteUsers.map { it.toEntity() }
            // With the unique index on remoteId, this will update existing users and add new ones
            userDao.insertUsers(entities)
            
            // Optional: Remove local users that have a remoteId but are not in the fetched list
            // (meaning they were deleted on the server)
            // For a production app, we'd handle this more carefully.
        } catch (e: Exception) {
            Log.e(TAG, "Error refreshing users: ${e.message}", e)
            throw e
        }
    }

    private fun scheduleSync() {
        Log.d(TAG, "Scheduling sync worker...")
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(constraints)
            .build()

//        workManager.enqueue(syncRequest)
        workManager.enqueueUniqueWork(
            "sync_users_work",
            ExistingWorkPolicy.REPLACE, // Ou KEEP, dependendo da estratégia
            syncRequest
        )
    }
}
