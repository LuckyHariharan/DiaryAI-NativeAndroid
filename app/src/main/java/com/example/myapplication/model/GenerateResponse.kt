import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.Chat

class GeminiClient(private val modelName: String, private val apiKey: String) {
    private val generativeModel = GenerativeModel(modelName, apiKey)
    private var chatSession: Chat? = null

    init {
        chatSession = generativeModel.startChat()
    }

    suspend fun sendMessage(prompt: String): String {
        return try {
            val response = chatSession?.sendMessage(prompt)
            response?.text ?: "Sorry, I couldn't understand that."
        } catch (e: Exception) {
            "Error occurred: ${e.message}"
        }
    }
}
