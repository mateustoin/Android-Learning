package com.example.studyapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studyapp.ui.theme.StudyAppTheme
import com.google.android.material.color.MaterialColors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.studyapp.core.navigation.AppNavigation

const val TAG = "StudyAppMainActivity" // TAG

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            StudyAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
//                        App()
                        AppNavigation()
                    }
                }
            }
        }
    }
}

@Composable
fun Titulo(texto: String) {
    Text(text = "Texto: $texto", color = Color.White)
}

@Composable
fun Contador() {
    var count by rememberSaveable { mutableIntStateOf(0) } // estado observável e persistido na composição
    Log.d(TAG, "Composable Contador(): $count")
    Button(onClick = { count++ }) {
        Text("Contador: $count")   // UI depende do estado
    }
}

@Composable
fun CounterRow() {
    var count by remember { mutableIntStateOf(0) }
    Log.d(TAG, "Composable CounterRow(): $count")
    Row(modifier = Modifier.padding(16.dp)) {
        Button(onClick = { count++ }) {
            Text("Increment")
        }
        Button(onClick = { count-- }) {
            Text("Decrement")
        }
        Text("Count: $count")
    }
}

@Composable
fun CaracterCounter() {
    var text by remember { mutableStateOf("") }
    var charCount by remember { mutableStateOf(0) }
    Log.d(TAG, "Composable CaracterCounter(): $text:$charCount")

    Row(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = text,
            onValueChange = {
                text = it
                charCount = it.length
            },
            label = { Text("Enter text") }
        )
        Text("Character count: $charCount")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onNavigateToSecond: () -> Unit,
               onAddName: (String) -> Unit) {
    var text by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

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
                Icon(Icons.Default.ArrowForward, contentDescription = "Navegar Tela")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(horizontal = 32.dp),
               verticalArrangement = Arrangement.Center,
               horizontalAlignment = Alignment.CenterHorizontally) {
            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
                },
                label = { Text("Digite um nome") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(Modifier.height(16.dp))
            Button(onClick = {
                if (text.isNotBlank()){
                    onAddName(text)
                    Toast.makeText(context, "Nome $text adicionado!", Toast.LENGTH_SHORT).show()
                    text = ""
                } else {
                    Toast.makeText(context, "Nome vazio!", Toast.LENGTH_SHORT).show()
                }
            }, Modifier.fillMaxWidth()) {
                Text("Insira Nome")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondScreenCompose(names: List<String>, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Lista de Nomes") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onBack()
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Adicionar Nome")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            LazyColumn {
                items(names) { name ->
                    Text(text = name, modifier = Modifier.padding(16.dp))
                    HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                }
            }
        }
    }
}

@Composable
fun App() {
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

class NamesViewModel : ViewModel() {
    private val _namesList = MutableStateFlow<List<String>>(emptyList())
    val namesList: StateFlow<List<String>> = _namesList.asStateFlow()

    fun addName(newName: String) {
        if (newName.isNotBlank()) {
            _namesList.value += newName
        }
    }
}

@Composable
fun DynamicListScreen() {
    // 1. Create a state-aware list
    val items = remember { mutableStateListOf<String>("Item 1", "Item 2") }

    Column {
        Button(onClick = {
            // 2. Add an item dynamically
            items.add("Item ${items.size + 1}")
        }) {
            Text("Add Item")
        }

        LazyColumn {
            // 3. Bind the list to LazyColumn
            items(items) { item ->
                Text(text = item, modifier = Modifier.padding(16.dp))
            }
        }
    }
}