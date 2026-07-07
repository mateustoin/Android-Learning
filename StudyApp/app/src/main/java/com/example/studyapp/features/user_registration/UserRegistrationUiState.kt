package com.example.studyapp.features.home

sealed interface UserRegistrationUiState {
    object Idle : UserRegistrationUiState
    //object IdleWithUserInformation: HomeUiState
    object Loading : UserRegistrationUiState
    data class SuccessAddingUser(val user: String) : UserRegistrationUiState
    data class ErrorAddingUser(val message: String) : UserRegistrationUiState
}
