package com.example.studyapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studyapp.feature.home.HomeScreen
import com.example.studyapp.feature.users.UserViewModel
import com.example.studyapp.feature.home.HomeViewModel
import com.example.studyapp.feature.users.UserListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = HomeRoute) {
        composable<HomeRoute> {
            val viewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                { navController.navigate(UserListRoute) },
                viewModel = viewModel)
        }
        composable<UserListRoute> {
            val viewModel: UserViewModel = hiltViewModel()
            UserListScreen(viewModel, onBack = { navController.popBackStack() })
        }
    }
}