package com.example.task1businesscard.presentation.navigation



sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Main : Screen("main")
    object Contacts : Screen("contacts")
    object Cards : Screen("cards")
    object Scan : Screen("scan")
    object Profile : Screen("profile")

    companion object {
        val bottomNavItems = listOf(Contacts, Cards, Scan, Profile)
    }
}