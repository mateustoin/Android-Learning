package com.example.studyapp.feature.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    // Starts with loading because the page just got opened
    private val _uiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    init {
        // Load users as soon as the ViewModel is created
        loadUsers()
    }

    fun refreshUsers() {
        loadUsers()
    }

    fun deleteUser(userId: Int) {
        viewModelScope.launch {
            try {
                repository.deleteUser(userId)
                loadUsers()
            } catch (e: Exception) {
                _uiState.value = UserUiState.ErrorLoadingUsers(e.message ?: "An error occurred during deleteUser")
            }
        }
    }

    private fun loadUsers() {
        // Create a coroutine (viewModelScope) to not freeze the UI
        viewModelScope.launch {
            _uiState.value = UserUiState.Loading
            try {
                val users = repository.getUsers()
                _uiState.value = UserUiState.SuccessLoadingUsers(users)
            } catch (e: Exception) {
                _uiState.value = UserUiState.ErrorLoadingUsers(e.message ?: "An error occurred during loadUsers")
            }
        }
    }
}