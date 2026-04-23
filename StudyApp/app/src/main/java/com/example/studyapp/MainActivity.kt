package com.example.studyapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.studyapp.ui.theme.StudyAppTheme

const val TAG = "StudyAppMainActivity" // TAG

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            StudyAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        Titulo("Teste testeee")
                        Contador()
                        CounterRow()
                        CaracterCounter()
                    }
                }
            }
        }
    }
}

//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//                Titulo("Teste testeee")
//        }
//    }
//}

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