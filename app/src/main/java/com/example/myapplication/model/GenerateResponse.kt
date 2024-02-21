package com.example.myapplication.model

import com.google.ai.client.generativeai.GenerativeModel

class GeminiClient(modelName: String, apiKey: String) {
    private val generativeModel: GenerativeModel = GenerativeModel(modelName, apiKey)

    suspend fun generateResponse(prompt: String): String {
        val refinedPrompt = "Respond to this prompt using CBT principles and be kind. max 4-5 sentences, prompt is : $prompt"
        return try {
            val response = generativeModel.generateContent(refinedPrompt)
            processResponse(response.text)
        } catch (e: Exception) {
            "Sorry, there was an error: ${e.localizedMessage}"
        }
    }

    private fun processResponse(response: String?): String {
        return response?.let {
            it.lines().take(4).joinToString(" ") // Limit to 4 sentences for brevity
        } ?: "Sorry, I couldn't understand that."
    }
}
