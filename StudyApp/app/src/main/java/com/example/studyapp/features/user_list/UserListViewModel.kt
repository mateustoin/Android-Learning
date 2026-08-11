package com.example.studyapp.features.user_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyapp.domain.usecase.DeleteUserUseCase
import com.example.studyapp.domain.usecase.GetUsersUseCase
import com.example.studyapp.domain.usecase.RefreshUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val refreshUsersUseCase: RefreshUsersUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    init {
        loadUsers()
    }

    fun refreshUsers() {
        viewModelScope.launch {
            try {
                refreshUsersUseCase()
            } catch (e: Exception) {
                // If the current state is already SuccessLoadingUsers, we don't want to overwrite it with an error.
                // This allows the user to see cached data even if the refresh fails (e.g., when offline).
                if (_uiState.value !is UserUiState.SuccessLoadingUsers) {
                    _uiState.value = UserUiState.ErrorLoadingUsers(e.message ?: "An error occurred during refresh")
                }
                android.util.Log.e("UserViewModel", "Failed to refresh users", e)
            }
        }
    }

    fun deleteUser(userId: Long) {
        viewModelScope.launch {
            try {
                deleteUserUseCase(userId)
            } catch (e: Exception) {
                _uiState.value = UserUiState.ErrorLoadingUsers(e.message ?: "An error occurred during delete")
            }
        }
    }

    private fun loadUsers() {
        viewModelScope.launch {
            _uiState.value = UserUiState.Loading
            getUsersUseCase().collect { users ->
                _uiState.value = UserUiState.SuccessLoadingUsers(users)
            }
        }
    }
}
