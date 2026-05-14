package com.example.studyapp.feature.home

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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.example.studyapp.feature.home.HomeUiState
import com.example.studyapp.feature.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onNavigateToSecond: () -> Unit,
               viewModel : HomeViewModel) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var text by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val isButtonEnabled = text.isNotBlank() && state !is HomeUiState.Loading

    LaunchedEffect(state) {
        when (state) {
            is HomeUiState.SuccessAddingUser -> {
                Toast.makeText(context, "User ${(state as HomeUiState.SuccessAddingUser).user} added!", Toast.LENGTH_SHORT).show()
                viewModel.onEventFinished()
            }
            is HomeUiState.ErrorAddingUser -> {
                Toast.makeText(context, "Error: ${(state as HomeUiState.ErrorAddingUser).message}", Toast.LENGTH_SHORT).show()
                viewModel.onEventFinished()
            }
            else -> {}
        }
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Tela Principal") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onNavigateToSecond()
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Navegar Tela")
            }
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
                    Text("Insert")
                }
            }

            if (state is HomeUiState.Loading) {
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