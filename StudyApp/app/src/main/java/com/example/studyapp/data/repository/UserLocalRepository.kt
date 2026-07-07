package com.example.studyapp.data.repository

import com.example.studyapp.data.local.room.dao.UserDao
import com.example.studyapp.data.local.room.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import com.example.studyapp.domain.model.User
import com.example.studyapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserLocalRepository @Inject constructor(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun getUsers(): Flow<List<User>> {
        return userDao.getAllUsers().map { entities ->
            entities.map { entity ->
                User(
                    id = entity.id,
                    name = entity.name,
                    email = entity.email,
                    created_at = entity.created_at
                )
            }
        }
    }

    override suspend fun addUser(user: User) {
        userDao.insertUser(UserEntity(
            id = user.id,
            name = user.name,
            email = user.email,
            created_at = user.created_at
        ))
    }

    override suspend fun deleteUser(userId: Long) {
        userDao.deleteUser(userId)
    }

    override suspend fun refreshUsers() {
        TODO("Not yet implemented")
    }
}
