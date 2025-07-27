package com.example.task1businesscard.presentation.navigation


import androidx.annotation.DrawableRes
import com.example.task1businesscard.R

sealed class BottomNavItem(
    val route: String,
    val title: String,
    @DrawableRes val icon: Int
) {
    object Contacts : BottomNavItem("contacts", "Contacts", R.drawable.contacts_24)
    object Cards : BottomNavItem("cards", "Cards", R.drawable.card_24)
    object Scan : BottomNavItem("scan", "Scan", R.drawable.scanner_24)
    object Profile : BottomNavItem("profile", "Profile", R.drawable.profile_24)
}


