package com.example.studyapp.data.repository

import com.example.studyapp.data.model.UserApiModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Interface to define what the repository should do
interface UserRepository {
    suspend fun getUsers(): List<UserApiModel>
    suspend fun addUser(user: UserApiModel)
    suspend fun deleteUser(userId: Int)
}
