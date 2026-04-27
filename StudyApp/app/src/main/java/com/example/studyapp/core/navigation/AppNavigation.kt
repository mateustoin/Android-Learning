package com.example.studyapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studyapp.HomeScreen
import com.example.studyapp.SecondScreenCompose

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
//    val viewModel: NamesViewModel = NamesViewModel()
    val names = remember { mutableStateListOf<String>("") }

    NavHost(navController, startDestination = "HomeScreen") {
        composable("HomeScreen") {
            HomeScreen(
                { navController.navigate("SecondScreen") },
//                onAddName = { newName -> viewModel.addName(newName) })
                onAddName = { newName -> names.add(newName) })
        }
        composable("SecondScreen") {
            SecondScreenCompose(names = names, onBack = { navController.popBackStack() })
        }
    }
}