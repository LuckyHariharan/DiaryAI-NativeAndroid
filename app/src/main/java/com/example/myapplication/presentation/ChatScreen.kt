package com.example.myapplication.presentation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import java.text.SimpleDateFormat
import java.util.*

data class Message(val text: String, val timestamp: String, val isUser: Boolean)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(viewModel: ChatViewModel) {
    var messageText by remember { mutableStateOf("") }
    val messages = viewModel.getMessages()
    var showColorDialog by remember { mutableStateOf(false) }
    var backgroundColor by remember { mutableStateOf(Color(0xFF1C1C1C)) } // Default color

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AI Diary Chat") },
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
                .padding(innerPadding),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp)
            ) {
                MessagesList(
                    messages = messages,
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = messageText,
                        onValueChange = { messageText = it },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                            .height(56.dp), // Increased height for the text field
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.White, // Background color of the TextField
                            textColor = Color.Black, // Text color
                            cursorColor = Color.Black // Cursor color
                        )
                    )
                    Button(
                        onClick = {
                            viewModel.sendMessage(messageText)
                            messageText = ""
                        },
                        modifier = Modifier.padding(start = 8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = White)
                    ) {
                        Text("Send", color = Color.Black)
                    }                }
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
    // Color selection dialog and MessagesList function remain the same
}



@Composable
fun ColorSelectionDialog(onColorSelected: (Color) -> Unit, onDismissRequest: () -> Unit) {
    val colors = listOf(
        Color(0xFF000000), // Black
        Color(0xFF191970), // Midnight Blue
        Color(0xFF000080), // Navy
        Color(0xFF00008B), // Dark Blue
        Color(0xFF0000CD), // Medium Blue
        Color(0xFF0000FF), // Blue
        Color(0xFF008000), // Green
        Color(0xFF008080), // Teal
        Color(0xFF008B8B), // Dark Cyan
        Color(0xFF00BFFF), // Deep Sky Blue
        Color(0xFF2E8B57), // Sea Green
        Color(0xFF2F4F4F), // Dark Slate Gray
        Color(0xFF32CD32), // Lime Green
        Color(0xFF36454F), // Charcoal
        Color(0xFF3CB371), // Medium Sea Green
        Color(0xFF40E0D0), // Turquoise
        Color(0xFF4169E1), // Royal Blue
        Color(0xFF4682B4), // Steel Blue
        Color(0xFF483D8B), // Dark Slate Blue
        Color(0xFF48D1CC), // Medium Turquoise
        Color(0xFF4B0082), // Indigo
        Color(0xFF556B2F), // Dark Olive Green
        Color(0xFF5F9EA0), // Cadet Blue
        Color(0xFF6495ED), // Cornflower Blue
        Color(0xFF663399), // Rebecca Purple
        Color(0xFF696969), // Dim Gray
        Color(0xFF6A5ACD), // Slate Blue
        Color(0xFF6B8E23), // Olive Drab
        Color(0xFF708090), // Slate Gray
        Color(0xFF778899), // Light Slate Gray
        Color(0xFF808080), // Gray
        Color(0xFF3D3D3D), // Dark Gray (replacing Silver)
        Color(0xFF2C2C2C), // Darker Gray
        Color(0xFF899088), // Gray
        Color(0xFF6F2D2C), // Darker Gray

    )
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Select a Background Color") },
        text = {
            LazyVerticalGrid(
                modifier = Modifier.padding(vertical = 8.dp),
                columns = GridCells.Fixed(5)
            ) {
                items(colors) { color ->
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
fun getCurrentTimestamp(): String {
    val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return sdf.format(Date())
}

@Composable
fun MessagesList(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            // Differentiate color and layout between user and AI messages
            val (bubbleColor, timestampAlignment, textAlignment) = if (message.isUser) {
                Triple(White, Alignment.CenterVertically, Alignment.CenterStart)
            } else {
                Triple(Color(0xFFD3D3D3), Alignment.CenterVertically, Alignment.CenterEnd)
            }

            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                horizontalArrangement = if (message.isUser) Arrangement.Start else Arrangement.End
            ) {
                if (!message.isUser) {
                    Text(
                        text = message.timestamp,
                        modifier = Modifier
                            .align(timestampAlignment)
                            .padding(end = 4.dp),
                        color = White
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .wrapContentSize(textAlignment)
                ) {
                    Card(
                        modifier = Modifier.padding(4.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        // Set the background color of the content inside the Card
                        Box(modifier = Modifier
                            .background(bubbleColor)
                            .padding(8.dp)) {
                            Text(
                                text = message.text,
                                color = Color.Black
                            )
                        }
                    }
                }

                if (message.isUser) {
                    Text(
                        text = message.timestamp,
                        modifier = Modifier
                            .align(timestampAlignment)
                            .padding(start = 4.dp),
                        color = White
                    )
                }
            }
        }
    }
}
