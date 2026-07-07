package com.example.studyapp.features.user_registration

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.studyapp.viewmodel.UserRegistrationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserRegistrationScreen( onNavigateToUserList: () -> Unit,
               viewModel : UserRegistrationViewModel
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var text by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val isButtonEnabled = text.isNotBlank() && state !is UserRegistrationUiState.Loading

    LaunchedEffect(state) {
        when (state) {
            is UserRegistrationUiState.SuccessAddingUser -> {
                Toast.makeText(context, "User ${(state as UserRegistrationUiState.SuccessAddingUser).user} added!", Toast.LENGTH_SHORT).show()
                viewModel.onEventFinished()
            }
            is UserRegistrationUiState.ErrorAddingUser -> {
                Toast.makeText(context, "Error: ${(state as UserRegistrationUiState.ErrorAddingUser).message}", Toast.LENGTH_SHORT).show()
                viewModel.onEventFinished()
            }
            else -> {}
        }
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("User Registration") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = onNavigateToUserList) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Get back to User List"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Insert name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(Modifier.height(16.dp))
                Button(onClick = {
                    viewModel.addUser(text)
                    text = "" },
                    enabled = isButtonEnabled,
                    modifier = Modifier.fillMaxWidth()) {
                    Text("Register")
                }
            }

            if (state is UserRegistrationUiState.Loading) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
        }
    }
}