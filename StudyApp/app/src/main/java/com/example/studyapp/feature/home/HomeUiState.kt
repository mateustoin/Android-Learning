package com.example.studyapp.feature.home

sealed interface HomeUiState {
    object Idle : HomeUiState
    //object IdleWithUserInformation: HomeUiState
    object Loading : HomeUiState
    data class SuccessAddingUser(val user: String) : HomeUiState
    data class ErrorAddingUser(val message: String) : HomeUiState
}
