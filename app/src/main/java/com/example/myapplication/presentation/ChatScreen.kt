package com.example.myapplication.presentation

import androidx.compose.foundation.background
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background( Color(R.color.midnight_blue)), // Dark Charcoal Background
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(modifier = Modifier.padding(bottom = 8.dp)) { // Bottom padding for the entire Column
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
