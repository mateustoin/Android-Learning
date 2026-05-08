package com.example.studyapp.feature.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyapp.data.FakeUserRepository
import com.example.studyapp.data.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    // Change for Hilt later, manual instance for now
    private val repository: UserRepository = FakeUserRepository()

    // Starts with loading because the app just got opened
    private val _uiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    init {
        // Load users as soon as the ViewModel is created
        loadUsers()
    }

    private fun loadUsers() {
        // Create a coroutine (viewModelScope) to not freeze the UI
        viewModelScope.launch {
            _uiState.value = UserUiState.Loading
            try {
                val users = repository.getUsers()
                _uiState.value = UserUiState.Success(users)
            } catch (e: Exception) {
                _uiState.value = UserUiState.Error(e.message ?: "An error occurred during loadUsers")
            }
        }
    }

    fun addUser(name: String) {
        if (name.isBlank()) return

        viewModelScope.launch {
            _uiState.value = UserUiState.Loading
            try {
                repository.addUser(name)
                loadUsers()
            } catch (e: Exception) {
                _uiState.value = UserUiState.Error(e.message ?: "An error occurred while adding user")
            }
        }
    }
}