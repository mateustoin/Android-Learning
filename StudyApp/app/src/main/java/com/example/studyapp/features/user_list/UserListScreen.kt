package com.example.studyapp.features.user_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudSync
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.studyapp.data.local.preferences.AppTheme
import com.example.studyapp.MainViewModel
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
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (state) {
                is UserUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Loading...")
                    }
                }
                is UserUiState.SuccessLoadingUsers -> {
                    val users = (state as UserUiState.SuccessLoadingUsers).users
                    if (users.isNotEmpty()) {
                        LazyColumn {
                            items(
                                items = users,
                                key = { it.id ?: it.remoteId ?: it.hashCode() },
                                contentType = { "user_item" }
                            ) { user ->
                                ListItem(
                                    headlineContent = {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(text = user.name)
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Icon(
                                                imageVector = if (user.isSynced) Icons.Default.CloudDone else Icons.Default.CloudSync,
                                                contentDescription = if (user.isSynced) "Synced" else "Not Synced",
                                                modifier = Modifier.size(16.dp),
                                                tint = if (user.isSynced) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                                            )
                                        }
                                    },
                                    supportingContent = user.email?.let { { Text(text = it) } },
                                    leadingContent = {
                                        AsyncImage(
                                            model = user.avatarUrl,
                                            contentDescription = "User Avatar",
                                            modifier = Modifier
                                                .size(40.dp)
                                                .clip(CircleShape),
                                            contentScale = ContentScale.Crop,
                                            placeholder = rememberVectorPainter(Icons.Default.Person),
                                            error = rememberVectorPainter(Icons.Default.Person)
                                        )
                                    },
                                    trailingContent = {
                                        IconButton(onClick = {
                                            android.util.Log.i(TAG, "UserListScreen: Delete User (${user.name}/id: ${user.id})")
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
                        }
                    } else {
                        EmptyUserListState()
                    }
                }
                is UserUiState.ErrorLoadingUsers -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Error: ${(state as UserUiState.ErrorLoadingUsers).message}", color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        viewModel.refreshUsers()
    }
}

@Composable
fun EmptyUserListState() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset("loading.json")
        //LottieCompositionSpec.Url("https://assets4.lottiefiles.com/packages/lf20_zyquagfl.json")
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(250.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = "User List is empty!",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "Try adding some users or refreshing the list.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
