package com.example.studyapp.data.repository

import com.example.studyapp.core.network.RetrofitClient
import com.example.studyapp.data.api.UserApiService
import com.example.studyapp.data.model.UserApiModel

//class UserApiRepository @Inject constructor(
//    private val apiService: UserApiService
//) : UserRepository {
//
//    override suspend fun getUsers(): List<User> {
//        return apiService.getAllUsers().map { User(name = it.name) }
//    }
//
//    override suspend fun addUser(name: String) {
//        apiService.addUser(UserApiModel(name = name))
//    }
//}

class UserApiRepository : UserRepository {
    private val apiService: UserApiService = RetrofitClient.retrofit.create(UserApiService::class.java)

    override suspend fun getUsers(): List<User> {
        return apiService.getAllUsers().map { User(name = it.name) }
    }

    override suspend fun addUser(name: String) {
        apiService.addUser(UserApiModel(name = name))
    }
}