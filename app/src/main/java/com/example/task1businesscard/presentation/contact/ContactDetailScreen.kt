package com.example.task1businesscard.presentation.contact


import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.example.task1businesscard.domain.model.Contact
import com.example.task1businesscard.presentation.components.AvatarView
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailScreen(
    contact: Contact,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val scrollState = rememberLazyListState()
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val expandedSections = remember { mutableStateMapOf<String, Boolean>() }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text(contact.name) },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.clip(MaterialTheme.shapes.small)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { scope.launch { sheetState.show() } },
                        modifier = Modifier.clip(MaterialTheme.shapes.small)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = "Share Contact")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onEditClick) {
                Icon(Icons.Default.Edit, contentDescription = "Edit")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            state = scrollState,
            contentPadding = innerPadding,
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    AvatarView(name = contact.name)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Phone Section
            item {
                ExpandableInfoCard(
                    icon = Icons.Default.Phone,
                    title = "Phone",
                    value = contact.phone,
                    isExpanded = expandedSections["phone"] ?: false,
                    onExpandToggle = { expandedSections["phone"] = !(expandedSections["phone"] ?: false) },
                    trailingContent = {
                        IconButton(
                            onClick = {
                                clipboardManager.setText(AnnotatedString(contact.phone))
                                Toast.makeText(context, "Phone copied", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.clip(MaterialTheme.shapes.small)
                        ) {
                            Icon(Icons.Default.ContentCopy, contentDescription = "Copy Phone")
                        }
                    },
                    expandedContent = {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Divider(modifier = Modifier.padding(vertical = 8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                FilledTonalButton(
                                    onClick = { makePhoneCall(context, contact.phone) },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(Icons.Default.Call, contentDescription = null)
                                    Spacer(Modifier.width(8.dp))
                                    Text("Call")
                                }
                                FilledTonalButton(
                                    onClick = { sendSms(context, contact.phone) },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(Icons.Default.Message, contentDescription = null)
                                    Spacer(Modifier.width(8.dp))
                                    Text("Message")
                                }
                            }
                        }
                    }
                )
            }

            // Email Section
            item {
                ExpandableInfoCard(
                    icon = Icons.Default.Email,
                    title = "Email",
                    value = contact.email,
                    isExpanded = expandedSections["email"] ?: false,
                    onExpandToggle = { expandedSections["email"] = !(expandedSections["email"] ?: false) },
                    trailingContent = {
                        IconButton(
                            onClick = {
                                clipboardManager.setText(AnnotatedString(contact.email))
                                Toast.makeText(context, "Email copied", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.clip(MaterialTheme.shapes.small)
                        ) {
                            Icon(Icons.Default.ContentCopy, contentDescription = "Copy Email")
                        }
                    },
                    expandedContent = {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Divider(modifier = Modifier.padding(vertical = 8.dp))
                            FilledTonalButton(
                                onClick = { sendEmail(context, contact) },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(Icons.Default.Email, contentDescription = null)
                                Spacer(Modifier.width(8.dp))
                                Text("Send Email")
                            }
                        }
                    }
                )
            }

            // Company Section
            item {
                ExpandableInfoCard(
                    icon = Icons.Default.Business,
                    title = "Company",
                    value = contact.company,
                    isExpanded = expandedSections["company"] ?: false,
                    onExpandToggle = { expandedSections["company"] = !(expandedSections["company"] ?: false) },
                    trailingContent = {
                        IconButton(
                            onClick = {
                                clipboardManager.setText(AnnotatedString(contact.company))
                                Toast.makeText(context, "Company copied", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.clip(MaterialTheme.shapes.small)
                        ) {
                            Icon(Icons.Default.ContentCopy, contentDescription = "Copy Company")
                        }
                    }
                )
            }

            // Notes Section
            item {
                ExpandableInfoCard(
                    icon = Icons.Default.Notes,
                    title = "Notes",
                    value = contact.notes,
                    isExpanded = expandedSections["notes"] ?: false,
                    onExpandToggle = { expandedSections["notes"] = !(expandedSections["notes"] ?: false) },
                    trailingContent = {
                        IconButton(
                            onClick = {
                                clipboardManager.setText(AnnotatedString(contact.notes))
                                Toast.makeText(context, "Notes copied", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.clip(MaterialTheme.shapes.small)
                        ) {
                            Icon(Icons.Default.ContentCopy, contentDescription = "Copy Notes")
                        }
                    }
                )
            }
        }
    }

    // Fixed Bottom Sheet that animates from bottom
    if (sheetState.isVisible) {
        ModalBottomSheet(
            onDismissRequest = { scope.launch { sheetState.hide() } },
            sheetState = sheetState,
            modifier = Modifier
                .fillMaxWidth()

                .wrapContentHeight()
                .clip(MaterialTheme.shapes.large),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .verticalScroll(rememberScrollState())
                    .navigationBarsPadding()
                    .imePadding() // Add padding for keyboard

            ) {
                // Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Share,
                        contentDescription = null,
                        modifier = Modifier.size(28.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Share Contact",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                // Share options
                val shareOptions = listOf(
                    ShareOption(
                        icon = Icons.Default.Message,
                        title = "Share via Message",
                        action = { shareViaMessage(context, contact) }
                    ) to Color(0xFF4CAF50),
                    ShareOption(
                        icon = Icons.Default.Email,
                        title = "Share via Email",
                        action = { shareViaEmail(context, contact) }
                    ) to Color(0xFF2196F3),
                    ShareOption(
                        icon = Icons.Default.ContentCopy,
                        title = "Copy Details",
                        action = { copyContactDetails(context, contact) }
                    ) to Color(0xFF9C27B0),
                    ShareOption(
                        icon = Icons.Default.Share,
                        title = "More Options",
                        action = { shareWithSystemPicker(context, contact) }
                    ) to MaterialTheme.colorScheme.primary
                )

                shareOptions.forEach { (option, color) ->
                    ShareOptionItem(
                        option = option,
                        color = color,
                        onClick = {
                            scope.launch { sheetState.hide() }
                            option.action()
                        }
                    )
                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        thickness = 0.5.dp,
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                    )
                }
            }
        }
    }
}

@Composable
private fun ExpandableInfoCard(
    icon: ImageVector,
    title: String,
    value: String,
    isExpanded: Boolean,
    onExpandToggle: () -> Unit,
    trailingContent: @Composable () -> Unit,
    expandedContent: @Composable (() -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onExpandToggle
            ),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                )
        ) {
            ListItem(
                headlineContent = { Text(title) },
                supportingContent = { Text(value) },
                leadingContent = {
                    Icon(icon, contentDescription = title)
                },
                trailingContent = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        trailingContent()
                        if (expandedContent != null) {
                            IconButton(
                                onClick = onExpandToggle,
                                modifier = Modifier.size(48.dp)
                            ) {
                                Icon(
                                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                                    modifier = Modifier
                                        .size(24.dp)
                                        .rotate(
                                            animateFloatAsState(
                                                targetValue = if (isExpanded) 0f else 180f,
                                                animationSpec = tween(300)
                                            ).value
                                        )
                                )
                            }
                        }
                    }
                }
            )

            AnimatedVisibility(
                visible = isExpanded && expandedContent != null,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 72.dp, end = 16.dp, bottom = 16.dp)
                ) {
                    expandedContent?.invoke()
                }
            }
        }
    }
}

@Composable
private fun ShareOptionItem(
    option: ShareOption,
    color: Color,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                option.icon,
                contentDescription = option.title,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = option.title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

private data class ShareOption(
    val icon: ImageVector,
    val title: String,
    val action: () -> Unit
)

private fun makePhoneCall(context: Context, phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = android.net.Uri.parse("tel:$phoneNumber")
    }
    context.startActivity(intent)
}

private fun sendSms(context: Context, phoneNumber: String) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = android.net.Uri.parse("sms:$phoneNumber")
    }
    context.startActivity(intent)
}

private fun sendEmail(context: Context, contact: Contact) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = android.net.Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf(contact.email))
        putExtra(Intent.EXTRA_SUBJECT, "Regarding ${contact.name}")
    }
    context.startActivity(intent)
}

private fun shareViaMessage(context: Context, contact: Contact) {
    val shareText = formatContactDetails(contact)
    val intent = Intent(Intent.ACTION_VIEW).apply {
        type = "vnd.android-dir/mms-sms"
        putExtra("sms_body", shareText)
    }
    context.startActivity(intent)
}

private fun shareViaEmail(context: Context, contact: Contact) {
    val shareText = formatContactDetails(contact)
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "message/rfc822"
        putExtra(Intent.EXTRA_EMAIL, arrayOf(contact.email))
        putExtra(Intent.EXTRA_SUBJECT, "Contact: ${contact.name}")
        putExtra(Intent.EXTRA_TEXT, shareText)
    }
    context.startActivity(Intent.createChooser(intent, "Send Email"))
}

private fun copyContactDetails(context: Context, contact: Contact) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboard.setPrimaryClip(ClipData.newPlainText(
        "Contact Details",
        formatContactDetails(contact)
    ))
    Toast.makeText(context, "Contact copied", Toast.LENGTH_SHORT).show()
}

private fun shareWithSystemPicker(context: Context, contact: Contact) {
    val shareText = formatContactDetails(contact)
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, shareText)
    }
    context.startActivity(Intent.createChooser(intent, "Share via"))
}

private fun formatContactDetails(contact: Contact): String {
    return """
        Contact Details:
        Name: ${contact.name}
        Phone: ${contact.phone}
        Email: ${contact.email}
        Company: ${contact.company}
        Notes: ${contact.notes}
    """.trimIndent()



}