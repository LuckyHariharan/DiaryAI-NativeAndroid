// MainActivity.kt
package com.example.myapplication

import ChatViewModel
import GeminiClient
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myapplication.presentation.ChatScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val geminiClient = GeminiClient("gemini-pro", "AIzaSyDp0Oji17ZQ7dds8ODNFO1DaQ_oJ0ooEoc")
        val viewModel = ChatViewModel(geminiClient)


        setContent {
            ChatScreen(viewModel = viewModel)
        }
    }
}
