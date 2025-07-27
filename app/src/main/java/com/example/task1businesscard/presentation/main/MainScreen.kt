package com.example.task1businesscard.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ContactPage
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import com.example.bca.presentation.contact.ContactsScreen
import com.example.task1businesscard.presentation.cards.CardsScreen
import com.example.task1businesscard.presentation.contact.ContactDetailScreen
import com.example.task1businesscard.presentation.contact.ContactViewModel
import com.example.task1businesscard.presentation.contact.EditContactScreen
import com.example.task1businesscard.presentation.navigation.Screen
import com.example.task1businesscard.presentation.profile.ProfileScreen
import com.example.task1businesscard.presentation.scan.ScanScreen


@Composable
fun MainScreen(navController1: NavHostController) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val contactViewModel: ContactViewModel = viewModel()

    Scaffold(
        bottomBar = {
            if (currentRoute in listOf(
                    Screen.Contacts.route,
                    Screen.Cards.route,
                    Screen.Scan.route,
                    Screen.Profile.route
                )
            ) {
                BottomNavigationBar(
                    currentRoute = currentRoute,
                    onItemClick = { route ->
                        navController.navigate(route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Contacts.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Contacts.route) {
                ContactsScreen(
                    onCardClick = { contact ->
                        navController.navigate("contact_detail/${contact.id}")
                    }
                )
            }
            composable(Screen.Cards.route) { CardsScreen() }
            composable(Screen.Scan.route) { ScanScreen() }
            composable(Screen.Profile.route) { ProfileScreen() }


            composable("contact_detail/{contactId}") { backStackEntry ->
                val contactId = backStackEntry.arguments?.getString("contactId")?.toIntOrNull()
                val contact = contactViewModel.contacts.collectAsState().value.find { it.id == contactId }

                contact?.let {
                    ContactDetailScreen(
                        contact = it,
                        onEditClick = {
                            navController.navigate("edit_contact/${it.id}")
                        },
                        onBackClick = { navController.popBackStack() }
                    )
                } ?: Text("Contact not found")

            }
            composable("edit_contact/{contactId}") { backStackEntry ->
                val contactId = backStackEntry.arguments?.getString("contactId")?.toIntOrNull()
                val contact = contactViewModel.contacts.collectAsState().value.find { it.id == contactId }

                contact?.let {
                    EditContactScreen(
                        contact = it,
                        onSave = { updated ->
                            contactViewModel.updateContact(updated) //  This will trigger recomposition
                            // Save logic here (e.g. update local list or DB)
                            navController.popBackStack() // Go back to details after save
                        }
                    )
                }?: Text("Contact not found")
            }

        }
    }
}

@Composable
fun BottomNavigationBar(
    currentRoute: String?,
    onItemClick: (String) -> Unit
) {
    val navItems = listOf(
        Triple(Screen.Contacts, Icons.Default.ContactPage, "Contacts"),
        Triple(Screen.Cards, Icons.Default.CreditCard, "Cards"),
        Triple(Screen.Scan, Icons.Default.QrCodeScanner, "Scan"),
        Triple(Screen.Profile, Icons.Default.AccountBox, "Profile"),
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
    ) {
        navItems.forEach { (screen, icon, label) ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = { onItemClick(screen.route) },
                icon = { Icon(imageVector = icon, contentDescription = label) },
                label = { Text(label) },
                alwaysShowLabel = false
            )
        }
    }
}