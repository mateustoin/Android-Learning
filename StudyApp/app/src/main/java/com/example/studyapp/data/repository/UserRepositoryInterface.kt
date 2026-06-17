package com.example.studyapp.data.repository

import com.example.studyapp.data.local.entity.UserEntity
import com.example.studyapp.data.model.UserApiModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

// Interface to define what the repository should do
interface UserRepository {
//    suspend fun getUsers(): Flow<List<UserApiModel>>
    suspend fun getUsers(): Flow<List<UserEntity>>
//    suspend fun addUser(user: UserApiModel)
    suspend fun addUser(user: UserEntity)
    suspend fun deleteUser(userId: Int)
}
