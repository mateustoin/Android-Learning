package com.example.studyapp.features.user_registration

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyapp.domain.model.User
import com.example.studyapp.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserRegistrationViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    // Starts with Idle because the app just got opened
    private val _uiState = MutableStateFlow<UserRegistrationUiState>(UserRegistrationUiState.Idle)
    val uiState: StateFlow<UserRegistrationUiState> = _uiState.asStateFlow()

    fun onEventFinished() {
        _uiState.value = UserRegistrationUiState.Idle
    }

    fun addUser(name: String) {
        if (name.isBlank()) return

        viewModelScope.launch {
            _uiState.value = UserRegistrationUiState.Loading
            try {
                val user = User(name = name)
                repository.addUser(user = user)
                _uiState.value = UserRegistrationUiState.SuccessAddingUser(name)
                Log.i(TAG, "Sucesso adicionando user")
//                _uiState.value = UserRegistrationUiState.Idle
            } catch (e: Exception) {
                Log.i(TAG, "Falha adicionando user")
                Log.i(TAG, "${e.message}")
                _uiState.value = UserRegistrationUiState.ErrorAddingUser(e.message ?: "An error occurred while adding user")
            }
        }
    }

    companion object {
        private const val TAG = "UserRegistrationViewModel"
    }
}