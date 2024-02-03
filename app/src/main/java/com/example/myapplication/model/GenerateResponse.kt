package com.example.myapplication.model

import com.google.ai.client.generativeai.GenerativeModel


class GeminiClient(private val modelName: String, private val apiKey: String) {
    private val generativeModel = GenerativeModel(modelName, apiKey)

    suspend fun generateResponse(prompt: String): String {
        return try {
            val response = generativeModel.generateContent(prompt)
            response.text ?: "Sorry, I couldn't understand that."
        } catch (e: Exception) {
            "Error occurred: ${e.message}"
        }
    }
}
