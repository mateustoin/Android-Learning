package com.example.studyapp.feature.home

import com.example.studyapp.feature.home.HomeUiState

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyapp.data.FakeUserRepository
import com.example.studyapp.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    // Starts with Idle because the app just got opened
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Idle)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun onEventFinished() {
        _uiState.value = HomeUiState.Idle
    }

    fun addUser(name: String) {
        if (name.isBlank()) return

        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                repository.addUser(name)
                _uiState.value = HomeUiState.SuccessAddingUser(name)
//                _uiState.value = HomeUiState.Idle
            } catch (e: Exception) {
                _uiState.value = HomeUiState.ErrorAddingUser(e.message ?: "An error occurred while adding user")
            }
        }
    }
}