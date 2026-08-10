package com.example.studyapp.features.user_registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyapp.domain.model.User
import com.example.studyapp.domain.usecase.AddUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserRegistrationViewModel @Inject constructor(
    private val addUserUseCase: AddUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserRegistrationUiState>(UserRegistrationUiState.Idle)
    val uiState: StateFlow<UserRegistrationUiState> = _uiState.asStateFlow()

    private val _eventChannel = Channel<UserRegistrationUiEvent>()
    val eventFlow = _eventChannel.receiveAsFlow()

    fun addUser(name: String) {
        if (name.isBlank()) return

        viewModelScope.launch {
            _uiState.value = UserRegistrationUiState.Loading
            try {
                addUserUseCase(User(name = name))
                _eventChannel.send(UserRegistrationUiEvent.Success("User $name added!"))
                _uiState.value = UserRegistrationUiState.Idle
            } catch (e: Exception) {
                _eventChannel.send(UserRegistrationUiEvent.Error(e.message ?: "An error occurred"))
                _uiState.value = UserRegistrationUiState.Idle
            }
        }
    }
}
