package com.example.studyapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyapp.data.local.preferences.AppTheme
import com.example.studyapp.data.local.preferences.ThemePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val themePreferences: ThemePreferences
) : ViewModel() {

    // Converts Flow to StateFlow
    val themeState: StateFlow<AppTheme> = themePreferences.themeFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AppTheme.SYSTEM
        )

    fun toggleTheme(currentTheme: AppTheme) {
        viewModelScope.launch {
            val nextTheme = when (currentTheme) {
                AppTheme.LIGHT -> AppTheme.DARK
                AppTheme.DARK -> AppTheme.SYSTEM
                AppTheme.SYSTEM -> AppTheme.LIGHT
            }
            themePreferences.saveTheme(nextTheme)
        }
    }
}
