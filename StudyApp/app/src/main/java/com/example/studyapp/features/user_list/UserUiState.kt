package com.example.studyapp.features.user_list

import androidx.compose.runtime.Immutable
import com.example.studyapp.domain.model.User

@Immutable
sealed interface UserUiState {
    @Immutable
    object Loading : UserUiState

    @Immutable
    data class SuccessLoadingUsers(val users: List<User>) : UserUiState

    @Immutable
    data class ErrorLoadingUsers(val message: String) : UserUiState
}
