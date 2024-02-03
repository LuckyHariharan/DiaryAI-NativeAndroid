package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myapplication.presentation.ChatScreen
import com.example.myapplication.presentation.ChatViewModel
import com.google.ai.client.generativeai.GenerativeModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize GenerativeModel as per documentation
        val generativeModel = GenerativeModel(
            modelName = "gemini-pro",
            apiKey = "AIzaSyDp0Oji17ZQ7dds8ODNFO1DaQ_oJ0ooEoc" // Ensure this is set up correctly
        )

        val viewModel = ChatViewModel(generativeModel)

        setContent {
            ChatScreen(viewModel = viewModel)
        }
    }
}
