package com.example.studyapp.domain.usecase

import com.example.studyapp.domain.model.User
import com.example.studyapp.domain.repository.UserRepository
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User) {
        repository.addUser(user)
    }
}
