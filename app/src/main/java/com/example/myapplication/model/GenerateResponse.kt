package com.example.myapplication.model

import com.google.ai.client.generativeai.GenerativeModel

suspend fun generateResponse(prompt: String): String {
    val generativeModel = GenerativeModel(
        modelName = "gemini-pro", // replace with your actual model name
        apiKey = "AIzaSyDp0Oji17ZQ7dds8ODNFO1DaQ_oJ0ooEoc" // Replace with your actual API key
    )

    val response = generativeModel.generateContent(prompt)
    return response.text ?: "Sorry, I couldn't understand that."
}
