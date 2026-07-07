package com.example.studyapp.domain.repository

import com.example.studyapp.domain.model.User
import kotlinx.coroutines.flow.Flow

// Interface to define what the repository should do
interface UserRepository {
//    suspend fun getUsers(): Flow<List<UserApiModel>>
    suspend fun getUsers(): Flow<List<User>>
//    suspend fun addUser(user: UserApiModel)
    suspend fun addUser(user: User)
    suspend fun deleteUser(userId: Long)
    suspend fun refreshUsers()
}
