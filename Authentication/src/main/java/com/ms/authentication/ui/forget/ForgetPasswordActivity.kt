package com.ms.authentication.ui.forget

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ForgetPasswordScreen() {
    Scaffold(modifier = Modifier
        .fillMaxSize()) {
        LazyColumn(contentPadding = it) {
            item {
                Text(text = "Welcome to Forget Password Screen", style = MaterialTheme.typography.headlineLarge)
            }
        }
    }
}