//package com.example.myapplication.presentation
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.material3.Button
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextField
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ChatBoxScreen(viewModel: DiaryViewModel) {
//    val userMessages by viewModel.userMessages.observeAsState(emptyList())
//
//    Column {
//        // Display user messages
//        userMessages.forEach { message ->
//            Text(text = "User: $message")
//        }
//
//        // Add a TextField for the user to enter messages
//        var newMessage: String by remember { mutableStateOf("") }
//        TextField(
//            value = newMessage,
//            onValueChange = { newMessage = it },
//            label = { Text(text = "Enter message") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        // Add a button to send the message
//        Button(
//            onClick = {
//                viewModel.addMessage(newMessage)
//                newMessage = ""
//            },
//            modifier = Modifier.align(Alignment.End)
//        ) {
//            Text(text = "Send")
//        }
//    }
//}