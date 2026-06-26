package com.example.studyapp.data.repository

import com.example.studyapp.data.api.UserApiService
import com.example.studyapp.data.local.dao.UserDao
import com.example.studyapp.data.mapper.toApiModel
import com.example.studyapp.data.mapper.toEntity
import com.example.studyapp.data.mapper.toUser
import com.example.studyapp.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineUserRepository @Inject constructor(
    private val userDao: UserDao,
    private val apiService: UserApiService
): UserRepository {

    override suspend fun getUsers(): Flow<List<User>> {
        return userDao.getAllUsers().map { entities ->
            entities.map { it.toUser() }
        }
    }

    override suspend fun addUser(user: User) {
        // Save locally first
        userDao.insertUser(user.toEntity())
        
        // Attempt to sync with remote API
        try {
            apiService.addUser(user.toApiModel())
        } catch (e: Exception) {
            // In a full implementation, you might mark this record for later sync
            e.printStackTrace()
        }
    }

    override suspend fun deleteUser(userId: Long) {
        // Delete locally first
        userDao.deleteUser(userId)
        
        // Attempt to delete from remote API
        try {
            apiService.deleteUser("eq.$userId")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun refreshUsers() {
        try {
            // Fetch users from API
            val remoteUsers = apiService.getAllUsers()
            
            // Map API models to entities and sync with local DB
            val entities = remoteUsers.map { it.toEntity() }
            userDao.clearAndInsertUsers(entities)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
