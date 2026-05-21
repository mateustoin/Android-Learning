package com.example.studyapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studyapp.feature.users.UserViewModel
import com.example.studyapp.feature.home.UserRegistrationViewModel
import com.example.studyapp.feature.home.UserRegistrationScreen
import com.example.studyapp.feature.users.UserListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = UserListRoute) {
        composable<UserListRoute> {
            val viewModel: UserViewModel = hiltViewModel()
            UserListScreen(
                onNavigateToUserRegistration = { navController.navigate(UserRegistrationRoute) },
                viewModel = viewModel)
        }
        composable<UserRegistrationRoute> {
            val viewModel: UserRegistrationViewModel = hiltViewModel()
            UserRegistrationScreen(
                onNavigateToUserList = { navController.navigate(UserListRoute) },
                viewModel = viewModel)
        }
    }
}