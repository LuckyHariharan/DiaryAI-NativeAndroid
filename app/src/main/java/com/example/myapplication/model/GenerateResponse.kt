package com.example.myapplication.model

import com.google.ai.client.generativeai.GenerativeModel

class GeminiClient(private val modelName: String, private val apiKey: String) {

    private val generativeModel = GenerativeModel(modelName, apiKey)

    suspend fun generateResponse(prompt: String): String {
        val response = generativeModel.generateContent(prompt)
        return response.text ?: "Sorry, I couldn't understand that."
    }
}