package com.example.studyapp.data.repository

import com.example.studyapp.core.network.RetrofitClient
import com.example.studyapp.data.api.UserApiService
import com.example.studyapp.data.model.UserApiModel
import javax.inject.Inject

class UserApiRepository @Inject constructor(
    private val apiService: UserApiService
) : UserRepository {

    override suspend fun getUsers(): List<UserApiModel> {
        return apiService.getAllUsers().map { UserApiModel(name = it.name, id = it.id) }
    }

    override suspend fun addUser(user: UserApiModel) {
        apiService.addUser(user)
    }

    override suspend fun deleteUser(userId: Int) {
        val response = apiService.deleteUser("eq.$userId")
//        if (!response.isSuccessful) {
//            throw Exception("Failed to delete user: ${response.code()}")
//        }
    }
}
