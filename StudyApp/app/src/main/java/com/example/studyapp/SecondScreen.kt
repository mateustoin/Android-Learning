package com.example.studyapp

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Log.d("SecondScreen", "onCreate da aplicação!")

        val text = intent.getStringExtra("text")
        // OBS: Pode ser val, pois a referência não muda, o que muda é o conteúdo do objeto ao qual ela se refere
        val mainText = findViewById<TextView>(R.id.text_second_screen)
        if (text != "") {
            mainText.text = text
        } else {
            mainText.text = "Nenhum texto enviado!"
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("SecondScreen", "onStart da aplicação!")
    }

    override fun onResume() {
        super.onResume()
        Log.d("SecondScreen", "onResume da aplicação!")
    }

    override fun onPause() {
        super.onPause()
        Log.d("SecondScreen", "onPause da aplicação!")
    }

    override fun onStop() {
        super.onStop()
        Log.d("SecondScreen", "onStop da aplicação!")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("SecondScreen", "onDestroy da aplicação!")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("SecondScreen", "onRestart da aplicação!")
    }
}