package com.example.task1businesscard.presentation.contact


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.task1businesscard.domain.model.Contact
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun ContactListItem(
    contact: Contact,
    modifier: Modifier = Modifier,
    onFavoriteClick: (Contact) -> Unit,
    onDeleteClick: (Contact) -> Unit,
    onClick: (Contact) -> Unit // ✅ Added

) {
    val buttonWidth = 70.dp
    val totalActions = 3
    val maxSwipe = with(LocalDensity.current) { (buttonWidth * totalActions + 20.dp).toPx() }

    val swipeOffset = remember { mutableStateOf(0f) }
    val scope = rememberCoroutineScope()
    var isVisible by remember { mutableStateOf(true) }

    AnimatedVisibility(
        visible = isVisible,
        exit = fadeOut() + shrinkVertically(),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .height(80.dp)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount ->
                            val newOffset = (swipeOffset.value + dragAmount).coerceIn(-maxSwipe, 0f)
                            swipeOffset.value = newOffset
                        },
                        onDragEnd = {
                            scope.launch {
                                if (swipeOffset.value < -maxSwipe / 2) {
                                    swipeOffset.value = -maxSwipe
                                } else {
                                    swipeOffset.value = 0f
                                }
                            }
                        }
                    )
                }
        ) {
            // Background action buttons
            Row(
                modifier = Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(16.dp)),
                horizontalArrangement = Arrangement.End
            ) {
                ActionItem(icon = Icons.Default.Delete, color = Color.Red, label = "Delete") {
                    scope.launch {
                        isVisible = false
                        delay(250)
                        onDeleteClick(contact)
                    }
                }
                ActionItem(icon = Icons.Default.Call, color = Color.Green, label = "Call") {
                    println("Calling ${contact.name}")
                    scope.launch { swipeOffset.value = 0f }
                }
                ActionItem(icon = Icons.Default.Star, color = Color(0xFFFFC107), label = "Fav") {
                    onFavoriteClick(contact)
                    scope.launch { swipeOffset.value = 0f }
                }
            }

            // Foreground contact card
            Card(
                modifier = Modifier
                    .offset { IntOffset(swipeOffset.value.roundToInt(), 0) }
                    .fillMaxSize()
                    .clickable { onClick(contact) }, // ✅ Fix: pass contact here
                elevation = CardDefaults.cardElevation(6.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Avatar
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                brush = Brush.linearGradient(
                                    listOf(Color(0xFF6366F1), Color(0xFF9333EA))
                                ),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(android.graphics.Color.parseColor(contact.colorHex))),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = contact.name.first().uppercaseChar().toString(),
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = contact.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = contact.company,
                            style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                        )
                    }

                    IconButton(onClick = { onFavoriteClick(contact) }) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Favorite",
                            tint = if (contact.isFavorite) Color(0xFFFFC107) else Color.Gray
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun ActionItem(icon: ImageVector, color: Color, label: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(70.dp)
            .fillMaxHeight()
            .background(color)
            .clickable { onClick() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color.White
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}

