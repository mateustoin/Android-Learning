package com.example.studyapp.domain.usecase

import com.example.studyapp.domain.repository.UserRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(userId: Long) {
        repository.deleteUser(userId)
    }
}
