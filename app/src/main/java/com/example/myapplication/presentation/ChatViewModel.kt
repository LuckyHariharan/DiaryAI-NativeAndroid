import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.GeminiClient // Import GeminiClient
import com.example.myapplication.presentation.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
class ChatViewModel(private val geminiClient: GeminiClient) : ViewModel() {
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages = _messages.asStateFlow()

    fun sendMessage(messageText: String) {
        if (messageText.isNotEmpty()) {
            val updatedMessages = _messages.value.toMutableList()
            updatedMessages.add(Message(messageText, getCurrentTimestamp(), isUser = true))
            _messages.value = updatedMessages

            viewModelScope.launch {
                val aiResponse = geminiClient.generateResponse(messageText)
                val newMessages = _messages.value.toMutableList()
                newMessages.add(Message(aiResponse, getCurrentTimestamp(), isUser = false))
                _messages.value = newMessages
            }
        }
    }

    private fun getCurrentTimestamp(): String {
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }
}
