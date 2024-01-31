package com.example.myapplication.presentation

import androidx.lifecycle.ViewModel

class DiaryViewModel : ViewModel() {
    // LiveData holding user messages
    val userMessages = MutableLiveData<List<String>>()

    fun addMessage(message: String) {
        val updatedMessages = userMessages.value.orEmpty().toMutableList()
        updatedMessages.add(message)
        userMessages.value = updatedMessages
    }

    fun makeAIAPICall() {
        // Replace this with your actual AI API call using the Gemini API
        // For example:
        viewModelScope.launch {
            val aiResponse = GeminiAPI.makeCall()
            addMessage("AI: $aiResponse")
        }
    }
}