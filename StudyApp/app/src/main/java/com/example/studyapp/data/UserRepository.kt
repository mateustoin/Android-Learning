package com.example.studyapp.data

import kotlinx.coroutines.delay

// Basic model for User
data class User(val name: String)

// Interface to define what the repository should do
interface UserRepository {
    suspend fun getUsers(): List<User>
    suspend fun addUser(nome: String)
}

class FakeUserRepository : UserRepository {
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