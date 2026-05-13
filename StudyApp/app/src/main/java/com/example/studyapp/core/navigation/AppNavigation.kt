package com.example.studyapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studyapp.feature.home.HomeScreen
import com.example.studyapp.feature.names.SecondScreenCompose
import com.example.studyapp.feature.users.UserViewModel
import com.example.studyapp.feature.home.HomeViewModel
import com.example.studyapp.feature.users.UserListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val names = remember { mutableStateListOf<String>("") }


    NavHost(navController, startDestination = HomeRoute) {
        composable<HomeRoute> {
            val viewModel: HomeViewModel = viewModel()
            HomeScreen(
                { navController.navigate(UserListRoute) },
                viewModel = viewModel)
        }
        composable<UserListRoute> {
            val viewModel: UserViewModel = viewModel()
            UserListScreen(viewModel, onBack = { navController.popBackStack() })
        }
    }
}