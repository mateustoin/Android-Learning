package com.example.studyapp.data.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

// Basic model for User
data class User(val name: String)

// Interface to define what the repository should do
interface UserRepository {
    suspend fun getUsers(): List<User>
    suspend fun addUser(name: String)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
//        repository: FakeUserRepository
        repository: UserApiRepository
    ): UserRepository
}

class FakeUserRepository @Inject constructor() : UserRepository {
    // List to keep User information on Memory
    private val userList = mutableListOf<User>()

    override suspend fun getUsers(): List<User> {
        delay(1000) // Simulate network/database delay
        return userList.toList()
    }

    override suspend fun addUser(name: String) {
        delay(500) // Simulate network/database delay
        userList.add(User(name))
    }
}
