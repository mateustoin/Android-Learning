package com.example.studyapp.domain.usecase

import com.example.studyapp.domain.repository.UserRepository
import javax.inject.Inject

class RefreshUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke() {
        repository.refreshUsers()
    }
}
