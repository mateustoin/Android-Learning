package com.example.studyapp.features.user_registration

sealed interface UserRegistrationUiState {
    data object Idle : UserRegistrationUiState
    data object Loading : UserRegistrationUiState
}
