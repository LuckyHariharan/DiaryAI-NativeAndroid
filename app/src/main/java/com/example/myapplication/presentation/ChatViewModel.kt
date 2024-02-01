package com.example.myapplication.presentation// ChatViewModel.kt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ChatViewModel : ViewModel() {
    private val messages = mutableListOf<Message>()

    fun getMessages(): List<Message> {
        return messages
    }

    fun sendMessage(messageText: String) {
        if (messageText.isNotEmpty()) {
            messages.add(Message(messageText, getCurrentTimestamp(), isUser = true))

            // Simulated AI response
            val aiResponse = "AI Response to: $messageText"
            messages.add(Message(aiResponse, getCurrentTimestamp(), isUser = false))
        }
    }

    private fun getCurrentTimestamp(): String {
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }
}