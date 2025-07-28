package com.example.task1businesscard.presentation.cards

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
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

    // Card Color
    val presetColors = listOf(
        Color.White,
        Color(0xFF2196F3), // Blue
        Color(0xFF4CAF50), // Green
        Color(0xFF9C27B0), // Purple
        Color(0xFFFF9800), // Orange
        Color(0xFF6650a4), // Deep Purple
        Color(0xFFE91E63), // Pink
        Color(0xFF009688), // Teal
        Color(0xFFFF5722), // Deep Orange
        Color(0xFF607D8B), // Blue Grey
        Color(0xFF795548), // Brown
        Color(0xFF3F51B5), // Indigo
        Color(0xFF00BCD4), // Cyan
        Color(0xFF8BC34A), // Light Green
        Color(0xFFFFC107), // Amber
        Color(0xFF9E9E9E), // Grey
        Color(0xFF000000), // Black
    )
    // Add these text color options at the top of your file
    val textColors = listOf(
        Color.Black,
        Color.White,
        Color(0xFF2196F3), // Blue
        Color(0xFF00008B), // Dark Blue
        Color(0xFF4B0082), // Indigo
        Color(0xFF800000), // Maroon
        Color(0xFF8B0000), // Dark Red
        Color(0xFF006400), // Dark Green
        Color(0xFF808000), // Olive
        Color(0xFF708090), // Slate Gray
        Color(0xFF2F4F4F), // Dark Slate Gray
        Color(0xFFFF4500), // Orange Red
        Color(0xFFDAA520), // Golden Rod
        Color(0xFF800080), // Purple
        Color(0xFFA52A2A), // Brown
        Color(0xFFDC143C), // Crimson
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
            .padding(16.dp)

    ) {

        // Template selector
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("Professional", "Modern", "Minimal").forEachIndexed { index, label ->
                FilterChip(
                    selected = (index + 1) == selectedTemplate,
                    onClick = { selectedTemplate = index + 1 },
                    label = { Text(label) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Customization Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp) // Fixed height for better layout
        ) {



          // Card Color
            item {
                CustomizationCard("Card Color") {
                    val scrollState = rememberScrollState()

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(scrollState),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        presetColors.forEach { color ->
                            Box(
                                modifier = Modifier
                                    .size(40.dp) // Slightly larger for better touch target
                                    .background(color, CircleShape) // Circular color swatches
                                    .border(
                                        2.dp,
                                        if (color == selectedColor) Color.Black else Color.Transparent,
                                        CircleShape
                                    )
                                    .clickable { selectedColor = color }
                                    .padding(4.dp) // Inner padding for the border
                            )
                        }
                    }
                }
            }

            // Text Color
            item {
                CustomizationCard("Text Color") {
                    val scrollState = rememberScrollState()

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(scrollState),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        textColors.forEach { color ->
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(color, CircleShape)
                                    .border(
                                        2.dp,
                                        if (color == selectedTextColor) Color.Black else Color.Transparent,
                                        CircleShape
                                    )
                                    .clickable { selectedTextColor = color }
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                }
            }            // Font Selection
            item {
                CustomizationCard("Font") {
                    Box {
                        Text(
                            text = systemFonts.first { it.second == selectedFont }.first,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surfaceVariant)
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
                                containerColor = if (textAlignment == TextAlign.Start) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Icon(Icons.Default.FormatAlignLeft, "Left")
                        }
                        IconButton(
                            onClick = { textAlignment = TextAlign.Center },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = if (textAlignment == TextAlign.Center) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Icon(Icons.Default.FormatAlignCenter, "Center")
                        }
                        IconButton(
                            onClick = { textAlignment = TextAlign.End },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = if (textAlignment == TextAlign.End) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
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
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ){
                        FilterChip(
                            selected = logoShape == 0,
                            onClick = { logoShape = 0 },
                            label = { Text("Rectangle") },
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )
                        FilterChip(
                            selected = logoShape == 1,
                            onClick = { logoShape = 1 },
                            label = { Text("Circle") },
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )
                    }
                }
            }



            // Orientation
            item {
                CustomizationCard("Orientation") {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = isPortrait,
                            onClick = { isPortrait = true },
                            label = { Text("Portrait") },
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )

                        FilterChip(
                            selected = !isPortrait,
                            onClick = { isPortrait = false },
                            label = { Text("Landscape") },
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )
                    }
                }
            }

        }

        Spacer(modifier = Modifier.height(32.dp))

        // Card Preview
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            val cardModifier = Modifier
                .then(
                    if (isPortrait) {
                        Modifier
                            .fillMaxWidth(0.95f)
                            .aspectRatio(1.3f)
                    } else {
                        Modifier
                            .fillMaxHeight(0.9f) // Smaller height in landscape
                            .aspectRatio(1.3f) // keep same ratio, but rotate
                            .graphicsLayer {
                                rotationZ = 90f
                                transformOrigin = TransformOrigin(0.5f, 0.5f)
                            }

                    }
                )
                .then(
                    if (selectedTemplate != 1) {
                        Modifier.clip(RoundedCornerShape(16.dp))
                    } else Modifier
                )


            when (selectedTemplate) {
                1 -> ProfessionalCard(
                    backgroundColor = selectedColor,
                    textColor = selectedTextColor,
                    fontFamily = selectedFont,
                    textAlignment = textAlignment,
                    logoShape = logoShape,
                    modifier = cardModifier
                )
                2 -> ModernCard(
                    backgroundColor = selectedColor,
                    textColor = selectedTextColor,
                    fontFamily = selectedFont,
                    textAlignment = textAlignment,
                    logoShape = logoShape,
                    modifier = cardModifier
                )
                3 -> MinimalCard(
                    backgroundColor = selectedColor,
                    textColor = selectedTextColor,
                    fontFamily = selectedFont,
                    textAlignment = textAlignment,
                    modifier = cardModifier
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
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
    logoShape: Int
) {
    Box(
        modifier = modifier
            .background(backgroundColor) // Clean background
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Logo placeholder
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        color = textColor.copy(alpha = 0.2f),
                        shape = if (logoShape == 0) RectangleShape else CircleShape // ✅ Rectangle with no corner radius
                    )
            )

            Spacer(modifier = Modifier.height(16.dp))

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

            Spacer(modifier = Modifier.height(16.dp))

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
    }
}




@Composable
fun ModernCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    textColor: Color,
    fontFamily: FontFamily,
    textAlignment: TextAlign,
    logoShape: Int
) {
    Box(
        modifier = modifier
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
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    textColor: Color,
    fontFamily: FontFamily,
    textAlignment: TextAlign
) {
    Box(
        modifier = modifier
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