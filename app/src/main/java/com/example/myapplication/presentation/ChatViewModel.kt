import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.GeminiClient // Import GeminiClient
import com.example.myapplication.presentation.Message
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ChatViewModel(private val geminiClient: GeminiClient) : ViewModel() {
    private val messages = mutableListOf<Message>()

    fun getMessages(): List<Message> {
        return messages
    }

    fun sendMessage(messageText: String) {
        if (messageText.isNotEmpty()) {
            // Add user message to the list
            messages.add(Message(messageText, getCurrentTimestamp(), isUser = true))

            // Fetch AI response asynchronously
            viewModelScope.launch {
                val aiResponse = geminiClient.generateResponse(messageText)
                messages.add(Message(aiResponse, getCurrentTimestamp(), isUser = false))
            }
        }
    }

    private fun getCurrentTimestamp(): String {
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }
}
