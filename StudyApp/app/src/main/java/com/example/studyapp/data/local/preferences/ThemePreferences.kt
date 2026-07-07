package com.example.studyapp.data.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


enum class AppTheme {
    LIGHT, DARK, SYSTEM
}

@Singleton
class ThemePreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val themeKey = stringPreferencesKey("app_theme")

    // Expose the theme as a flow (reactive to changes)
    val themeFlow: Flow<AppTheme> = dataStore.data.map { preferences ->
        val themeName = preferences[themeKey]
        try {
            AppTheme.valueOf(themeName ?: AppTheme.SYSTEM.name)
        } catch (e: IllegalArgumentException) {
            AppTheme.SYSTEM
        }
    }

    suspend fun saveTheme(theme: AppTheme) {
        dataStore.edit { preferences ->
            preferences[themeKey] = theme.name
        }
    }
}