package com.example.studyapp.feature.home

sealed interface UserRegistrationUiState {
    object Idle : UserRegistrationUiState
    //object IdleWithUserInformation: HomeUiState
    object Loading : UserRegistrationUiState
    data class SuccessAddingUser(val user: String) : UserRegistrationUiState
    data class ErrorAddingUser(val message: String) : UserRegistrationUiState
}
