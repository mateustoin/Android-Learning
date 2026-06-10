package com.example.studyapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studyapp.data.preferences.AppTheme
import com.example.studyapp.feature.users.UserViewModel
import com.example.studyapp.feature.home.UserRegistrationViewModel
import com.example.studyapp.feature.home.UserRegistrationScreen
import com.example.studyapp.feature.users.UserListScreen
import com.example.studyapp.viewmodel.MainViewModel

@Composable
fun AppNavigation(mainViewModel: MainViewModel, themeState: AppTheme) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = UserListRoute) {
        composable<UserListRoute> {
            val viewModel: UserViewModel = hiltViewModel()
            UserListScreen(
                onNavigateToUserRegistration = { navController.navigate(UserRegistrationRoute) },
                viewModel = viewModel,
                mainViewModel = mainViewModel,
                themeState = themeState
            )
        }
        composable<UserRegistrationRoute> {
            val viewModel: UserRegistrationViewModel = hiltViewModel()
            UserRegistrationScreen(
                onNavigateToUserList = { navController.navigate(UserListRoute) },
                viewModel = viewModel)
        }
    }
}
