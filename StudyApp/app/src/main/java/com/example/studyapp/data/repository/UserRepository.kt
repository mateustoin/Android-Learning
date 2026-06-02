package com.example.studyapp.data.repository

import com.example.studyapp.data.model.UserApiModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

// Basic model for User
//data class User(val name: String)

// Interface to define what the repository should do
interface UserRepository {
    suspend fun getUsers(): List<UserApiModel>
    suspend fun addUser(user: UserApiModel)
    suspend fun deleteUser(userId: Int)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        repository: UserApiRepository
    ): UserRepository
}
