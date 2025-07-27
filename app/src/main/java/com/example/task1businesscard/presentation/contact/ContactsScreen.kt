package com.example.bca.presentation.contact

import android.app.Application
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.task1businesscard.domain.model.Contact
import com.example.task1businesscard.presentation.contact.ContactListItem
import com.example.task1businesscard.presentation.contact.ContactViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ContactsScreen(
    viewModel: ContactViewModel = viewModel(),
    onCardClick: (Contact) -> Unit // ✅ Add this parameter
) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val listState = rememberLazyListState()
    val contacts by viewModel.contacts.collectAsState()
    val scope = rememberCoroutineScope()

    var dragOffset by remember { mutableStateOf(0f) }
    var isRefreshing by remember { mutableStateOf(false) }
    val animatedOffset by animateFloatAsState(
        targetValue = if (isRefreshing) 80f else 0f,
        animationSpec = tween(300),
        label = "refreshOffset"
    )

    var showSearchBar by remember { mutableStateOf(true) }
    val previousScrollOffset = remember { mutableStateOf(0) }

    LaunchedEffect(listState) {
        snapshotFlow {
            listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset
        }.map { (index, offset) ->
            val currentOffset = index * 1000 + offset
            val isScrollingDown = currentOffset > previousScrollOffset.value
            previousScrollOffset.value = currentOffset
            isScrollingDown
        }.distinctUntilChanged().collectLatest { isScrollingDown ->
            showSearchBar = !isScrollingDown
        }
    }

    val filteredContacts = remember(searchQuery.text, contacts) {
        if (searchQuery.text.isEmpty()) contacts
        else contacts.filter {
            it.name.contains(searchQuery.text, ignoreCase = true)
        }
    }

    val grouped = filteredContacts.groupBy { it.name.first().uppercaseChar() }.toSortedMap()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectVerticalDragGestures(
                    onVerticalDrag = { _, dragAmount ->
                        if (listState.firstVisibleItemIndex == 0 && !isRefreshing) {
                            dragOffset = (dragOffset + dragAmount).coerceIn(0f, 200f)
                        }
                    },
                    onDragEnd = {
                        if (dragOffset > 100f && !isRefreshing) {
                            isRefreshing = true
                            scope.launch {
                                delay(1000)
                                viewModel.refreshContacts()
                                isRefreshing = false
                            }
                        }
                        dragOffset = 0f
                    }
                )
            }
    ) {
        Column(
            modifier = Modifier
                .offset(y = Dp(animatedOffset))
                .fillMaxSize()
        ) {
            AnimatedContent(
                targetState = showSearchBar,
                transitionSpec = {
                    if (targetState) {
                        slideInVertically(tween(300), { -it }) + fadeIn() with
                                slideOutVertically(tween(200), { it }) + fadeOut()
                    } else {
                        slideInVertically(tween(300), { it }) + fadeIn() with
                                slideOutVertically(tween(200), { -it }) + fadeOut()
                    }.using(SizeTransform(clip = false))
                },
                label = "searchBarAnimation"
            ) { visible ->
                if (visible) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        label = { Text("Search contacts...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    )
                }
            }

            if (filteredContacts.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text("No contacts found", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize()
                ) {
                    grouped.forEach { (initial, contacts) ->
                        if (contacts.isNotEmpty()) {
                            stickyHeader {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(MaterialTheme.colorScheme.background)
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                ) {
                                    Text(
                                        text = initial.toString(),
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            items(contacts, key = { it.name }) { contact ->
                                AnimatedVisibility(
                                    visible = true,
                                    enter = fadeIn(animationSpec = tween(500)) +
                                            slideInVertically(initialOffsetY = { it / 2 }, animationSpec = tween(500))
                                ) {
                                    ContactListItem(
                                        contact = contact,
                                        onFavoriteClick = { viewModel.toggleFavorite(it) },
                                        onDeleteClick = { viewModel.deleteContact(it) },
                                        onClick = { onCardClick(contact) } // ✅ Now works
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        if (isRefreshing) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 8.dp)
            ) {
                RefreshSpinner()
            }
        }

        AnimatedVisibility(
            visible = showSearchBar,
            enter = fadeIn() + slideInVertically { it },
            exit = fadeOut() + slideOutVertically { it },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            FloatingActionButton(
                onClick = {
                    // TODO: Add your FAB action here
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Action",
                    tint = Color.White
                )
            }
        }
    }
}


@Composable
fun RefreshSpinner() {
    CircularProgressIndicator(
        modifier = Modifier.size(32.dp),
        color = MaterialTheme.colorScheme.primary,
        strokeWidth = 3.dp
    )
}

