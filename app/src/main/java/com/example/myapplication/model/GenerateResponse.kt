package com.example.myapplication.model
suspend fun generateResponse() {
    // Initialize the generative model
    val generativeModel = GenerativeModel(
        modelName = "MODEL_NAME", // replace with your model name
        apiKey = BuildConfig.apiKey
    )

    // Define the prompt
    val prompt = "Your prompt here"

    // Generate the content
    val response = generativeModel.generateContent(prompt)

    // Update your UI with the response
    updateUI(response.text)
}

fun updateUI(response: String) {
    // This is where you update your UI with the response
    // For example, if you have a TextView to display the AI's response:
    yourTextView.text = response
}
