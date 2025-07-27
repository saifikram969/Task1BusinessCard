package com.example.task1businesscard.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bca.presentation.contact.ContactsScreen
import com.example.task1businesscard.presentation.cards.CardsScreen
import com.example.task1businesscard.presentation.main.MainScreen
import com.example.task1businesscard.presentation.profile.ProfileScreen
import com.example.task1businesscard.presentation.scan.ScanScreen
import com.example.task1businesscard.presentation.splash.SplashScreen


@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier
    ) {
        composable(Screen.Splash.route) { SplashScreen(navController) }
        composable(Screen.Main.route) { MainScreen(navController) }

        // Tab screens
        composable(Screen.Contacts.route) {
            ContactsScreen(
                onCardClick = { contact ->
                    // Navigate to contact detail (you need to set up the route first)
                    // Example: navController.navigate("contact_detail/${contact.id}")
                }
            )
        }

        composable(Screen.Cards.route) { CardsScreen() }
        composable(Screen.Scan.route) { ScanScreen() }
        composable(Screen.Profile.route) { ProfileScreen() }
    }
}