import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            addMessage(messageText, true)
            viewModelScope.launch {
                val aiResponse = geminiClient.sendMessage(messageText)
                addMessage(aiResponse, false)
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
