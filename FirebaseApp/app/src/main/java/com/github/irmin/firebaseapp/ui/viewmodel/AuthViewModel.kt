package com.github.irmin.firebaseapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.irmin.firebaseapp.data.model.User
import com.github.irmin.firebaseapp.data.repository.AuthRepository
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class AuthState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val currentUser: User? = null,
    val error: String? = null
)

class AuthViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    init {
        checkCurrentUser()
    }

    private fun checkCurrentUser() {
        authRepository.currentUser?.let { firebaseUser ->
            viewModelScope.launch {
                _authState.value = _authState.value.copy(isLoading = true)
                authRepository.getUserData(firebaseUser.uid).fold(
                    onSuccess = { user ->
                        _authState.value = AuthState(
                            isLoggedIn = true,
                            currentUser = user
                        )
                        updateFcmToken(user.uid)
                    },
                    onFailure = {
                        _authState.value = AuthState(isLoggedIn = false)
                    }
                )
            }
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = _authState.value.copy(isLoading = true, error = null)
            authRepository.signIn(email, password).fold(
                onSuccess = { firebaseUser ->
                    authRepository.getUserData(firebaseUser.uid).fold(
                        onSuccess = { user ->
                            _authState.value = AuthState(
                                isLoggedIn = true,
                                currentUser = user
                            )
                            updateFcmToken(user.uid)
                        },
                        onFailure = { e ->
                            _authState.value = AuthState(error = e.message)
                        }
                    )
                },
                onFailure = { e ->
                    _authState.value = AuthState(error = e.message ?: "Error al iniciar sesión")
                }
            )
        }
    }

    fun signUp(
        email: String,
        password: String,
        displayName: String,
        isAdmin: Boolean = false,
        adminPassword: String? = null
    ) {
        viewModelScope.launch {
            _authState.value = _authState.value.copy(isLoading = true, error = null)
            authRepository.signUp(email, password, displayName, isAdmin, adminPassword).fold(
                onSuccess = { firebaseUser ->
                    authRepository.getUserData(firebaseUser.uid).fold(
                        onSuccess = { user ->
                            _authState.value = AuthState(
                                isLoggedIn = true,
                                currentUser = user
                            )
                            updateFcmToken(user.uid)
                        },
                        onFailure = { e ->
                            _authState.value = AuthState(error = e.message)
                        }
                    )
                },
                onFailure = { e ->
                    _authState.value = AuthState(error = e.message ?: "Error al registrar")
                }
            )
        }
    }

    fun updateProfile(displayName: String) {
        viewModelScope.launch {
            _authState.value.currentUser?.let { currentUser ->
                _authState.value = _authState.value.copy(isLoading = true, error = null)
                val updatedUser = currentUser.copy(displayName = displayName)
                authRepository.updateUserData(updatedUser).fold(
                    onSuccess = {
                        _authState.value = _authState.value.copy(
                            isLoading = false,
                            currentUser = updatedUser
                        )
                    },
                    onFailure = { e ->
                        _authState.value = _authState.value.copy(
                            isLoading = false,
                            error = e.message
                        )
                    }
                )
            }
        }
    }

    private fun updateFcmToken(uid: String) {
        viewModelScope.launch {
            try {
                val token = FirebaseMessaging.getInstance().token.await()
                authRepository.updateFcmToken(uid, token)
            } catch (e: Exception) {
                // Silently fail token update
            }
        }
    }

    fun reloadUserData() {
        viewModelScope.launch {
            authRepository.currentUser?.let { firebaseUser ->
                _authState.value = _authState.value.copy(isLoading = true)
                authRepository.getUserData(firebaseUser.uid).fold(
                    onSuccess = { user ->
                        _authState.value = _authState.value.copy(
                            isLoading = false,
                            currentUser = user
                        )
                    },
                    onFailure = { e ->
                        _authState.value = _authState.value.copy(
                            isLoading = false,
                            error = e.message
                        )
                    }
                )
            }
        }
    }

    fun signOut() {
        authRepository.signOut()
        _authState.value = AuthState(isLoggedIn = false)
    }

    fun clearError() {
        _authState.value = _authState.value.copy(error = null)
    }
}

