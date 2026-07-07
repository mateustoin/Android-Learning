package com.example.studyapp.features.users

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SettingsSuggest
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.studyapp.data.preferences.AppTheme
import com.example.studyapp.viewmodel.UserViewModel
import com.example.studyapp.viewmodel.MainViewModel
import kotlin.collections.isNotEmpty

private const val TAG = "UserListScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    onNavigateToUserRegistration: () -> Unit,
    viewModel: UserViewModel,
    mainViewModel: MainViewModel,
    themeState: AppTheme
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("User List") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                actions = {
                    IconButton(onClick = { viewModel.refreshUsers() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh User List"
                        )
                    }
                    IconButton(onClick = { mainViewModel.toggleTheme(themeState) }) {
                        Icon(
                            imageVector = when(themeState) {
                                AppTheme.LIGHT -> Icons.Default.LightMode
                                AppTheme.DARK -> Icons.Default.DarkMode
                                else -> Icons.Default.SettingsSuggest
                            },
                            contentDescription = "Change Theme"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onNavigateToUserRegistration()
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add User")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            LazyColumn {
                when (state) {
                    is UserUiState.Loading -> {
                        item { Text("Loading...", modifier = Modifier.padding(16.dp)) }
                    }
                    is UserUiState.SuccessLoadingUsers -> {
                        val users = (state as UserUiState.SuccessLoadingUsers).users
                        if (users.isNotEmpty()){
                            items(users) { user ->
                                ListItem(
                                    headlineContent = { Text(text = user.name) },
                                    leadingContent = {
                                        Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = "User Avatar",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    },
                                    trailingContent = {
                                        IconButton(onClick = {
                                            Log.i(TAG, "UserListScreen: Delete User (${user.name}/id: ${user.id})")
                                            // user.id?.let garante que a função só vai ser chamada quando o id for não nulo
                                            user.id?.let { viewModel.deleteUser(user.id) }
                                        }) {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "Delete User",
                                                tint = MaterialTheme.colorScheme.error
                                            )
                                        }
                                    }
                                )
                                HorizontalDivider()
                            }
                        } else {
                            item {
                                Text(text = "User List is empty!", modifier = Modifier.padding(16.dp))
                            }
                        }

                    }
                    is UserUiState.ErrorLoadingUsers -> {
                        item { Text("Error: ${(state as UserUiState.ErrorLoadingUsers).message}", modifier = Modifier.padding(16.dp)) }
                    }
                }
            }
        }
    }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        viewModel.refreshUsers()
    }
}