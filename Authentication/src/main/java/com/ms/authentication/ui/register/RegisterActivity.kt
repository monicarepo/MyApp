package com.ms.authentication.ui.register

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun RegisterScreen() {
    Scaffold(modifier = Modifier
        .fillMaxSize()) {
        LazyColumn(contentPadding = it) {
            item {
                Text(text = "Welcome to Register Screen", style = MaterialTheme.typography.headlineLarge)
            }

        }
    }
}