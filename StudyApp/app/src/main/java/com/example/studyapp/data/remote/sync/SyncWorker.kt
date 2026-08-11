package com.example.studyapp.data.remote.sync

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.studyapp.data.local.room.dao.UserDao
import com.example.studyapp.data.mapper.toApiModel
import com.example.studyapp.data.mapper.toUser
import com.example.studyapp.data.remote.api.UserApiService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

private const val TAG = "SyncWorker"

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val userDao: UserDao,
    private val apiService: UserApiService
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        Log.i(TAG, "Starting synchronization...")
        return try {
            syncPendingDeletions()
            syncPendingAdditions()
            Log.i(TAG, "Synchronization completed successfully.")
            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Sync failed: ${e.message}", e)
            Result.retry()
        }
    }

    private suspend fun syncPendingDeletions() {
        val pendingDeletions = userDao.getPendingDeletions()
        if (pendingDeletions.isEmpty()) return

        Log.i(TAG, "Syncing ${pendingDeletions.size} pending deletions...")
        pendingDeletions.forEach { user ->
            try {
                user.remoteId?.let { remoteId ->
                    val response = apiService.deleteUser("eq.$remoteId")
                    if (response.isSuccessful) {
                        userDao.deleteUserPermanently(user.id)
                    } else {
                        Log.w(TAG, "Failed to delete user $remoteId: ${response.code()}")
                    }
                } ?: run {
                    userDao.deleteUserPermanently(user.id)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error syncing deletion for user ${user.id}: ${e.message}")
                throw e
            }
        }
    }

    private suspend fun syncPendingAdditions() {
        val pendingSync = userDao.getPendingSyncUsers()
        if (pendingSync.isEmpty()) return

        Log.i(TAG, "Syncing ${pendingSync.size} pending additions...")
        pendingSync.forEach { entity ->
            try {
                val apiModel = entity.toUser().toApiModel()
                val response = apiService.addUser(apiModel)
                
                if (response.isSuccessful) {
                    val remoteUser = response.body()?.firstOrNull()
                    val remoteId = remoteUser?.id?.toString()
                    if (remoteId != null) {
                        userDao.markAsSynced(entity.id, remoteId)
                    } else {
                        Log.e(TAG, "Server returned success but no ID for user ${entity.id}")
                    }
                } else {
                    Log.e(TAG, "Failed to add user ${entity.id}: ${response.code()} ${response.errorBody()?.string()}")
                    // If it's a client error (e.g. 400), we might not want to retry forever, but for now throw to trigger retry
                    throw Exception("API returned ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error syncing addition for user ${entity.id}: ${e.message}")
                throw e
            }
        }
    }
}
