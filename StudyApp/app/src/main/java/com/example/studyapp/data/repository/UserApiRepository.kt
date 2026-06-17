package com.example.studyapp.data.repository

import com.example.studyapp.data.api.UserApiService
import com.example.studyapp.data.local.entity.UserEntity
import com.example.studyapp.data.model.UserApiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserApiRepository @Inject constructor(
    private val apiService: UserApiService
) : UserRepository {

    override suspend fun getUsers(): Flow<List<UserEntity>> = flow {
        val apiUsers = apiService.getAllUsers()
        val userEntities = apiUsers.map { apiModel ->
            UserEntity(
                id = apiModel.id?.toLong() ?: 0L,
                name = apiModel.name,
                email = apiModel.email,
                created_at = apiModel.created_at
            )
        }
        emit(userEntities)
    }

    override suspend fun addUser(user: UserEntity) {
        val apiModel = UserApiModel(
            id = if (user.id == 0L) null else user.id.toInt(),
            name = user.name,
            email = user.email,
            created_at = user.created_at
        )
        apiService.addUser(apiModel)
    }

    override suspend fun deleteUser(userId: Int) {
        apiService.deleteUser("eq.$userId")
    }
}
