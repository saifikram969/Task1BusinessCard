package com.example.task1businesscard.presentation.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.task1businesscard.presentation.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    // Animation states
    var startAnimation by remember { mutableStateOf(false) }

    // Typing animation state
    val appName = "ContactCard"
    var typingText by remember { mutableStateOf("") }

    // Gradient animation progress
    val gradientProgress by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 2500),
        label = "gradientAnimation"
    )

    // Scale animation
    val scale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.8f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scaleAnimation"
    )

    // Alpha animation
    val alpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1000),
        label = "alphaAnimation"
    )

    // Gradient colors
    val gradientColors = listOf(
        Color(0xFF6366F1),
        Color(0xFF9333EA),
        Color(0xFFEC4899)
    )

    // Animated gradient brush
    val animatedGradient = Brush.linearGradient(
        colors = gradientColors,
        start = Offset(x = 1000f * (1 - gradientProgress), y = 0f),
        end = Offset(x = 0f, y = 0f)
    )
    // Custom logo composition
    val customLogo = @Composable {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // First line (styled text)
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = Color.White)) {
                        append("⧉ ") // Custom ASCII art logo
                    }
                    withStyle(
                        SpanStyle(
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 14.sp
                        )
                    ) {
                        append("Business")
                    }
                },
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.alpha(alpha * 0.9f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Main app name with typing effect
            Text(
                text = typingText,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 40.sp
                ),
                modifier = Modifier
                    .scale(scale)
                    .alpha(alpha)
            )
        }
    }
    // Start animations
    LaunchedEffect(Unit) {
        startAnimation = true

        // Typing animation
        appName.forEachIndexed { index, _ ->
            typingText = appName.take(index + 1)
            delay(100)
        }

        delay(2000)
        navController.navigate(Screen.Main.route) {
            popUpTo(Screen.Splash.route) { inclusive = true }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(animatedGradient)
    ) {
        customLogo()
    }
}