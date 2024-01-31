package com.example.myapplication.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DiaryScreen(viewModel: DiaryViewModel) {
    val diaryEntries by viewModel.diaryEntries.observeAsState(emptyList())
    val userMessages by viewModel.userMessages.observeAsState(emptyList())

    Column {
        diaryEntries.forEach { entry ->
            Text(text = entry)
        }

        // Display the chat box
        ChatBoxScreen(viewModel = viewModel)
    }
}
