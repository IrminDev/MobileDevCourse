package com.github.irmin.firebaseapp.ui.navigation

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.github.irmin.firebaseapp.ui.screens.*
import com.github.irmin.firebaseapp.ui.viewmodel.AuthViewModel
import com.github.irmin.firebaseapp.ui.viewmodel.NotificationViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    authViewModel: AuthViewModel = viewModel(),
    notificationViewModel: NotificationViewModel = viewModel()
) {
    val authState by authViewModel.authState.collectAsState()
    val notificationState by notificationViewModel.state.collectAsState()

    // Determinar pantalla inicial
    val startDestination = if (authState.isLoggedIn) Screen.Home.route else Screen.Login.route

    // Manejar cambios en el estado de autenticación
    LaunchedEffect(authState.isLoggedIn, authState.currentUser?.uid) {
        if (authState.isLoggedIn && authState.currentUser != null) {
            // Usuario logueado - cargar datos
            val user = authState.currentUser!!
            notificationViewModel.loadNotifications(user.uid)
            if (user.isAdmin) {
                notificationViewModel.loadUsers()
            }
        } else {
            // Usuario deslogueado - detener listeners
            notificationViewModel.stopListening()
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                isLoading = authState.isLoading,
                error = authState.error,
                onLogin = { email, password ->
                    authViewModel.signIn(email, password)
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onClearError = { authViewModel.clearError() }
            )

            // Navegar a Home cuando el login sea exitoso
            LaunchedEffect(authState.isLoggedIn) {
                if (authState.isLoggedIn) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            }
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                isLoading = authState.isLoading,
                error = authState.error,
                onRegister = { email, password, displayName, isAdmin, adminPassword ->
                    authViewModel.signUp(email, password, displayName, isAdmin, adminPassword)
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
                onClearError = { authViewModel.clearError() }
            )

            // Navegar a Home cuando el registro sea exitoso
            LaunchedEffect(authState.isLoggedIn) {
                if (authState.isLoggedIn) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            }
        }

        composable(Screen.Home.route) {
            authState.currentUser?.let { user ->
                HomeScreen(
                    currentUser = user,
                    notifications = notificationState.notifications,
                    onNavigateToProfile = {
                        navController.navigate(Screen.Profile.route)
                    },
                    onNavigateToUsers = {
                        notificationViewModel.loadUsers()
                        navController.navigate(Screen.UsersList.route)
                    },
                    onNavigateToSendNotification = {
                        notificationViewModel.loadUsers()
                        navController.navigate(Screen.SendNotification.route)
                    },
                    onSignOut = {
                        notificationViewModel.stopListening()
                        authViewModel.signOut()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                    },
                    onMarkAsRead = { notificationId ->
                        notificationViewModel.markAsRead(notificationId)
                    },
                    onReloadUser = {
                        authViewModel.reloadUserData()
                    }
                )
            }
        }

        composable(Screen.Profile.route) {
            authState.currentUser?.let { user ->
                ProfileScreen(
                    user = user,
                    isLoading = authState.isLoading,
                    onUpdateProfile = { displayName ->
                        authViewModel.updateProfile(displayName)
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(Screen.UsersList.route) {
            authState.currentUser?.let { currentUser ->
                UsersListScreen(
                    users = notificationState.users,
                    isLoading = notificationState.isLoading,
                    currentUserId = currentUser.uid,
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onRefresh = {
                        notificationViewModel.loadUsers()
                    }
                )
            }
        }

        composable(Screen.SendNotification.route) {
            authState.currentUser?.let { currentUser ->
                SendNotificationScreen(
                    users = notificationState.users,
                    selectedUsers = notificationState.selectedUsers,
                    isLoading = notificationState.isLoading,
                    sendSuccess = notificationState.sendSuccess,
                    error = notificationState.error,
                    currentUserId = currentUser.uid,
                    onToggleUserSelection = { userId ->
                        notificationViewModel.toggleUserSelection(userId)
                    },
                    onSelectAll = {
                        notificationViewModel.selectAllUsers()
                    },
                    onClearSelection = {
                        notificationViewModel.clearSelection()
                    },
                    onSendNotification = { title, body ->
                        notificationViewModel.sendNotification(
                            title = title,
                            body = body,
                            senderId = currentUser.uid,
                            senderName = currentUser.displayName
                        )
                    },
                    onClearSuccess = {
                        notificationViewModel.clearSendSuccess()
                    },
                    onClearError = {
                        notificationViewModel.clearError()
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

