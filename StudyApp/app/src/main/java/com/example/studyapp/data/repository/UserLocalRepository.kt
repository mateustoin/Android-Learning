package com.example.studyapp.data.repository

import com.example.studyapp.data.local.dao.UserDao
import com.example.studyapp.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import com.example.studyapp.data.model.UserApiModel
import javax.inject.Inject

class UserLocalRepository @Inject constructor(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun getUsers(): Flow<List<UserEntity>> {
        return userDao.getAllUsers()
    }

    override suspend fun addUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    override suspend fun deleteUser(userId: Int) {
        userDao.deleteUser(userId)
    }
}
