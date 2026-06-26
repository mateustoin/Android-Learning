package com.example.studyapp.data.repository

import com.example.studyapp.data.api.UserApiService
import com.example.studyapp.data.local.entity.UserEntity
import com.example.studyapp.data.model.UserApiModel
import com.example.studyapp.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserApiRepository @Inject constructor(
    private val apiService: UserApiService
) : UserRepository {

    override suspend fun getUsers(): Flow<List<User>> = flow {
        val apiUsers = apiService.getAllUsers()
        val users = apiUsers.map { apiModel ->
            User(
                id = apiModel.id?.toLong() ?: 0L,
                name = apiModel.name,
                email = apiModel.email,
                created_at = apiModel.created_at
            )
        }
        emit(users)
    }

    override suspend fun addUser(user: User) {
        val apiModel = UserApiModel(
            id = user.id,
            name = user.name,
            email = user.email,
            created_at = user.created_at
        )
        apiService.addUser(apiModel)
    }

    override suspend fun deleteUser(userId: Long) {
        apiService.deleteUser("eq.$userId")
    }

    override suspend fun refreshUsers() {
        TODO("Not yet implemented")
    }
}
