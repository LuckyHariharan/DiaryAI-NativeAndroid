package com.example.myapplication.presentation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import java.text.SimpleDateFormat
import java.util.*

data class Message(val text: String, val timestamp: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen() {
    var messageText by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<Message>() }
    var showColorDialog by remember { mutableStateOf(false) }
    var backgroundColor by remember { mutableStateOf(Color(0xFF1C1C1C)) } // Default color

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chat Screen") },
                actions = {
                    IconButton(onClick = { showColorDialog = true }) {
                        Icon(Icons.Filled.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(innerPadding), // Apply the inner padding given by the Scaffold
            contentAlignment = Alignment.BottomCenter
        ) {
            Column {
                MessagesList(messages)
                Spacer(modifier = Modifier.height(12.dp)) // Additional Spacer for more space above keyboard
                Row(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .padding(bottom = 8.dp), // Extra bottom padding for the input row
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = messageText,
                        onValueChange = { messageText = it },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp), // Horizontal spacing between chatbox and send button
                        colors = TextFieldDefaults.textFieldColors()
                    )
                    Button(
                        onClick = {
                            if (messageText.isNotEmpty()) {
                                messages.add(Message(messageText, getCurrentTimestamp()))
                                messageText = ""
                            }
                        }
                    ) {
                        Text("Send")
                    }
                }
            }
        }
    }

    // Color selection dialog
    if (showColorDialog) {
        ColorSelectionDialog(onColorSelected = { color ->
            backgroundColor = color
            showColorDialog = false
        }, onDismissRequest = { showColorDialog = false })
    }
}

@Composable
fun ColorSelectionDialog(onColorSelected: (Color) -> Unit, onDismissRequest: () -> Unit) {
    val colors = listOf(
        Color(0xFF191970), // Midnight Blue
        Color(0xFF36454F), // Charcoal
        Color(0xFF2F4F4F), // Dark Slate Gray
        Color(0xFF414A4C), // Outer Space
        Color(0xFF353839), // Onyx
        Color(0xFF343434)  // Jet
        // Add more colors as needed
    )

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Select a Background Color") },
        text = {
            Column {
                colors.forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(color)
                            .padding(4.dp)
                            .clickable { onColorSelected(color) }
                    )
                }
            }
        },
        confirmButton = { }
    )
}

@Composable
fun MessagesList(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth() // Ensure row takes up full width
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f, fill = false) // Using weight with fill = false
                        .wrapContentSize(Alignment.CenterStart)
                ) {
                    Card(
                        modifier = Modifier.padding(4.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(
                            text = message.text,
                            modifier = Modifier.padding(8.dp),
                            color = Color.Black
                        )
                    }
                }
                Text(
                    text = message.timestamp,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 4.dp),
                    color = Color.White
                )
            }
        }
    }
}

fun getCurrentTimestamp(): String {
    val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return sdf.format(Date())
}
