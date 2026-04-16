package com.example.studyapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.studyapp.SecondScreen

class ExampleFragment : Fragment(R.layout.fragment_example) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("FragmentView", "onCreate da aplicação, no fragment!")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.btn_send).setOnClickListener {
            sendText()
        }
    }

    fun sendText() {
        Log.d("FragmentView", "Botão clicado!")
        val editText = view?.findViewById<EditText>(R.id.edit_text)
        Log.d("FragmentView", "Texto digitado: ${editText?.text}")

        val intent = Intent(requireContext(), SecondScreen::class.java).apply {
            putExtra("text", editText?.text.toString())
        }
        startActivity(intent)
    }
}
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("HomeActivity", "onCreate da aplicação, no home activity!")
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("HomeActivity", "onStart da aplicação, no home activity!")
    }

    override fun onResume() {
        super.onResume()
        Log.d("HomeActivity", "onResume da aplicação, no home activity!")
    }

    override fun onPause() {
        super.onPause()
        Log.d("HomeActivity", "onPause da aplicação, no home activity!")
    }

    override fun onStop() {
        super.onStop()
        Log.d("HomeActivity", "onStop da aplicação, no home activity!")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("HomeActivity", "onDestroy da aplicação, no home activity!")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("HomeActivity", "onRestart da aplicação, no home activity!")
    }
}
