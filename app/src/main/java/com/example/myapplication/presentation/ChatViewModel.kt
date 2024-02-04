package com.example.myapplication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ChatViewModel(private val generativeModel: GenerativeModel) : ViewModel() {
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages = _messages.asStateFlow()

    fun sendMessage(messageText: String) {
        if (messageText.isNotEmpty()) {
            addMessage(messageText, true)

            viewModelScope.launch {
                viewModelScope.launch {
                    try {
                        val response = generativeModel.generateContent(messageText)
                        addMessage(response.text ?: "No response", false)
                    } catch (e: Exception) {
                        addMessage("Error occurred: ${e.localizedMessage}", false)
                        e.printStackTrace() // This will print the full stack trace to the log
                    }
                }

            }
        }
    }

    private fun addMessage(text: String, isUser: Boolean) {
        val newMessages = _messages.value.toMutableList().apply {
            add(Message(text, getCurrentTimestamp(), isUser))
        }
        _messages.value = newMessages
    }

    private fun getCurrentTimestamp(): String {
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }
}
