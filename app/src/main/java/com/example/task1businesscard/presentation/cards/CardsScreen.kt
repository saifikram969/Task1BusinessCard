package com.example.task1businesscard.presentation.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatAlignCenter
import androidx.compose.material.icons.filled.FormatAlignLeft
import androidx.compose.material.icons.filled.FormatAlignRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardsScreen() {
    var selectedTemplate by remember { mutableStateOf(1) }
    var selectedColor by remember { mutableStateOf(Color.White) }
    var selectedTextColor by remember { mutableStateOf(Color.Black) }
    var isPortrait by remember { mutableStateOf(true) }
    var selectedFont by remember { mutableStateOf(FontFamily.Default) }
    var textAlignment by remember { mutableStateOf(TextAlign.Start) }
    var logoShape by remember { mutableStateOf(0) } // 0 = rectangle, 1 = circle
    var expanded by remember { mutableStateOf(false) }

    val presetColors = listOf(
        Color.White,
        Color(0xFF2196F3), // Blue
        Color(0xFF4CAF50), // Green
        Color(0xFF9C27B0), // Purple
        Color(0xFFFF9800), // Orange
        Color(0xFF6650a4), // Orange
    )

    val systemFonts = listOf(
        "Default" to FontFamily.Default,
        "Sans" to FontFamily.SansSerif,
        "Serif" to FontFamily.Serif,
        "Mono" to FontFamily.Monospace
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            //.verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Template selector
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("Professional", "Modern", "Minimal").forEachIndexed { index, label ->
                Button(
                    onClick = { selectedTemplate = index + 1 },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (index + 1 == selectedTemplate) Color.Yellow else Color.Gray
                    )
                ) {
                    Text(label)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Customization Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
                .weight(1f)
        ) {
            // Card Color
            item {
                CustomizationCard("Card Color") {
                    Row {
                        presetColors.forEach { color ->
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(color)
                                    .border(
                                        2.dp,
                                        if (color == selectedColor) Color.Black else Color.Transparent
                                    )
                                    .clickable { selectedColor = color }
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                }
            }

            // Text Color
            item {
                CustomizationCard("Text Color") {
                    Row {
                        listOf(Color.Black, Color.White, Color(0xFF2196F3)).forEach { color ->
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(color)
                                    .border(
                                        2.dp,
                                        if (color == selectedTextColor) Color.Black else Color.Transparent
                                    )
                                    .clickable { selectedTextColor = color }
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                }
            }

            // Font Selection
            item {
                CustomizationCard("Font") {
                    Box {
                        Text(
                            text = systemFonts.first { it.second == selectedFont }.first,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.LightGray)
                                .padding(8.dp)
                                .clickable { expanded = true }
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            systemFonts.forEach { (name, font) ->
                                DropdownMenuItem(
                                    text = { Text(name, fontFamily = font) },
                                    onClick = {
                                        selectedFont = font
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // Text Alignment
            item {
                CustomizationCard("Alignment") {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(
                            onClick = { textAlignment = TextAlign.Start },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = if (textAlignment == TextAlign.Start) Color.LightGray else Color.White
                            )
                        ) {
                            Icon(Icons.Default.FormatAlignLeft, "Left")
                        }
                        IconButton(
                            onClick = { textAlignment = TextAlign.Center },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = if (textAlignment == TextAlign.Center) Color.LightGray else Color.White
                            )
                        ) {
                            Icon(Icons.Default.FormatAlignCenter, "Center")
                        }
                        IconButton(
                            onClick = { textAlignment = TextAlign.End },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = if (textAlignment == TextAlign.End) Color.LightGray else Color.White
                            )
                        ) {
                            Icon(Icons.Default.FormatAlignRight, "Right")
                        }
                    }
                }
            }

            // Logo Shape
            item {
                CustomizationCard("Logo Shape") {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = { logoShape = 0 },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (logoShape == 0) Color.LightGray else Color.White
                            ),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Rectangle")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = { logoShape = 1 },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (logoShape == 1) Color.LightGray else Color.White
                            ),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Circle")
                        }
                    }
                }
            }

            // Orientation
            item {
                CustomizationCard("Orientation") {
                    Button(
                        onClick = { isPortrait = !isPortrait },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isPortrait) Color.LightGray else Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (isPortrait) "Portrait" else "Landscape")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Card Preview
        Box(
            modifier = Modifier
                .fillMaxWidth()
               //.padding(bottom = 32.dp)
                .weight(1f, fill = false)  // This makes the box take minimum required height
        ) {
            when (selectedTemplate) {
                1 -> ProfessionalCard(
                    backgroundColor = selectedColor,
                    textColor = selectedTextColor,
                    fontFamily = selectedFont,
                    textAlignment = textAlignment,
                    isPortrait = isPortrait,
                    logoShape = logoShape
                )
                2 -> ModernCard(
                    backgroundColor = selectedColor,
                    textColor = selectedTextColor,
                    fontFamily = selectedFont,
                    textAlignment = textAlignment,
                    isPortrait = isPortrait,
                    logoShape = logoShape
                )
                3 -> MinimalCard(
                    backgroundColor = selectedColor,
                    textColor = selectedTextColor,
                    fontFamily = selectedFont,
                    textAlignment = textAlignment,
                    isPortrait = isPortrait
                )
            }
        }
    }
}

@Composable
fun CustomizationCard(
    title: String,
    content: @Composable BoxScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Box(content = content)
        }
    }
}

@Composable
fun ProfessionalCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    textColor: Color,
    fontFamily: FontFamily,
    textAlignment: TextAlign,
    isPortrait: Boolean,
    logoShape: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(if (isPortrait) 1.8f else 0.8f)
            .background(backgroundColor)
            .border(1.dp, Color.Gray)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Logo placeholder
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        color = Color.Blue,
                        shape = if (logoShape == 0) RoundedCornerShape(8.dp) else CircleShape
                    )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Name
            Text(
                text = "John Doe",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                fontFamily = fontFamily,
                textAlign = textAlignment
            )

            // Title
            Text(
                text = "Software Engineer",
                fontSize = 16.sp,
                color = textColor.copy(alpha = 0.7f),
                fontFamily = fontFamily,
                textAlign = textAlignment
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Contact info
            Text(
                text = "john.doe@example.com",
                fontSize = 14.sp,
                color = textColor,
                fontFamily = fontFamily,
                textAlign = textAlignment
            )
            Text(
                text = "+1 234 567 890",
                fontSize = 14.sp,
                color = textColor,
                fontFamily = fontFamily,
                textAlign = textAlignment
            )
            Text(
                text = "www.example.com",
                fontSize = 14.sp,
                color = textColor,
                fontFamily = fontFamily,
                textAlign = textAlignment
            )
        }
    }
}

@Composable
fun ModernCard(
    backgroundColor: Color,
    textColor: Color,
    fontFamily: FontFamily,
    textAlignment: TextAlign,
    isPortrait: Boolean,
    logoShape: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(if (isPortrait) 1.8f else 0.8f)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(backgroundColor, backgroundColor.copy(alpha = 0.7f))
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Name with bold typography
            Text(
                text = "JOHN DOE",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                fontFamily = fontFamily,
                textAlign = textAlignment
            )

            // Title
            Text(
                text = "SOFTWARE ENGINEER",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = textColor.copy(alpha = 0.8f),
                fontFamily = fontFamily,
                textAlign = textAlignment
            )

            Spacer(modifier = Modifier.weight(1f))

            // Contact info
            Column {
                Text(
                    text = "john.doe@example.com",
                    fontSize = 14.sp,
                    color = textColor,
                    fontFamily = fontFamily,
                    textAlign = textAlignment
                )
                Text(
                    text = "+1 234 567 890",
                    fontSize = 14.sp,
                    color = textColor,
                    fontFamily = fontFamily,
                    textAlign = textAlignment
                )
                Text(
                    text = "www.example.com",
                    fontSize = 14.sp,
                    color = textColor,
                    fontFamily = fontFamily,
                    textAlign = textAlignment
                )
            }
        }

        // Logo placeholder in bottom right
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(
                    color = textColor.copy(alpha = 0.2f),
                    shape = if (logoShape == 0) RoundedCornerShape(8.dp) else CircleShape
                )
                .align(Alignment.BottomEnd)
        )
    }
}

@Composable
fun MinimalCard(
    backgroundColor: Color,
    textColor: Color,
    fontFamily: FontFamily,
    textAlignment: TextAlign,
    isPortrait: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(if (isPortrait) 1.8f else 0.8f)
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = when (textAlignment) {
                TextAlign.Start -> Alignment.Start
                TextAlign.Center -> Alignment.CenterHorizontally
                TextAlign.End -> Alignment.End
                else -> Alignment.CenterHorizontally
            },
            verticalArrangement = Arrangement.Center
        ) {
            // Name with accent color
            Text(
                text = "John Doe",
                fontSize = 22.sp,
                color = textColor,
                fontFamily = fontFamily
            )

            // Divider
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .height(1.dp)
                    .background(textColor)
                    .padding(vertical = 8.dp)
            )

            // Title
            Text(
                text = "Software Engineer",
                fontSize = 16.sp,
                color = textColor.copy(alpha = 0.7f),
                fontFamily = fontFamily
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Contact info
            Text(
                text = "john.doe@example.com",
                fontSize = 14.sp,
                color = textColor,
                fontFamily = fontFamily
            )
            Text(
                text = "+1 234 567 890",
                fontSize = 14.sp,
                color = textColor,
                fontFamily = fontFamily
            )
        }
    }
}