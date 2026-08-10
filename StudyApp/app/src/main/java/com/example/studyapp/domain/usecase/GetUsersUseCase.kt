package com.example.studyapp.domain.usecase

import com.example.studyapp.domain.model.User
import com.example.studyapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): Flow<List<User>> {
        return repository.getUsers()
    }
}
