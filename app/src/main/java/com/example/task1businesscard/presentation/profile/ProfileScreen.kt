package com.example.task1businesscard.presentation.profile


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Large Circular Avatar with gradient
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.linearGradient(
                        listOf(Color(0xFF6366F1), Color(0xFFEC4899))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "SK", // You can set initials dynamically
                color = Color.White,
                fontSize = 36.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sample text (you can remove later)
        Text("Profile Screen", fontSize = 24.sp)
    }
}
