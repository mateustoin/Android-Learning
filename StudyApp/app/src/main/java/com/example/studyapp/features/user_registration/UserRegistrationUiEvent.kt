package com.example.studyapp.features.user_registration

sealed class UserRegistrationUiEvent {
    data class Success(val message: String) : UserRegistrationUiEvent()
    data class Error(val message: String) : UserRegistrationUiEvent()
}
