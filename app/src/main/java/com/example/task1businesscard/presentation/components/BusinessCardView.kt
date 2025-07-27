package com.example.task1businesscard.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BusinessCardView(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    gradient: Brush? = null,
    cornerRadius: Dp = 0.dp,
    logoPlaceholderColor: Color = Color.Blue,
    name: String = "John Doe",
    title: String = "Software Engineer",
    email: String = "john.doe@example.com",
    phone: String = "+1 234 567 890",
    website: String = "www.example.com",
    textColor: Color = Color.Black,
    isMinimal: Boolean = false
) {
    val backgroundModifier = if (gradient != null) {
        Modifier.background(gradient, RoundedCornerShape(cornerRadius))
    } else {
        Modifier.background(backgroundColor, RoundedCornerShape(cornerRadius))
    }

    Box(
        modifier = modifier
            .then(backgroundModifier)
            .border(1.dp, Color.Gray.copy(alpha = 0.3f), RoundedCornerShape(cornerRadius))
            .padding(16.dp)
    ) {
        if (isMinimal) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = name,
                    fontSize = 22.sp,
                    color = textColor
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .height(1.dp)
                        .background(textColor)
                        .padding(vertical = 8.dp)
                )

                Text(
                    text = title,
                    fontSize = 16.sp,
                    color = textColor.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = email, fontSize = 14.sp, color = textColor)
                Text(text = phone, fontSize = 14.sp, color = textColor)
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(logoPlaceholderColor)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )

                Text(
                    text = title,
                    fontSize = 16.sp,
                    color = textColor.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(text = email, fontSize = 14.sp, color = textColor)
                Text(text = phone, fontSize = 14.sp, color = textColor)
                Text(text = website, fontSize = 14.sp, color = textColor)
            }
        }
    }
}