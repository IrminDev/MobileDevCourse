package com.github.irmin.firebaseapp.ui.navigation

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Home : Screen("home")
    data object Profile : Screen("profile")
    data object UsersList : Screen("users_list")
    data object SendNotification : Screen("send_notification")
}

