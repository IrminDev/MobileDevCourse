package com.github.irmin.firebaseapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.irmin.firebaseapp.data.model.Notification
import com.github.irmin.firebaseapp.data.model.User
import com.github.irmin.firebaseapp.data.repository.AuthRepository
import com.github.irmin.firebaseapp.data.repository.NotificationRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class NotificationState(
    val isLoading: Boolean = false,
    val notifications: List<Notification> = emptyList(),
    val users: List<User> = emptyList(),
    val selectedUsers: Set<String> = emptySet(),
    val sendSuccess: Boolean = false,
    val error: String? = null
)

class NotificationViewModel : ViewModel() {
    private val notificationRepository = NotificationRepository()
    private val authRepository = AuthRepository()

    private val _state = MutableStateFlow(NotificationState())
    val state: StateFlow<NotificationState> = _state.asStateFlow()

    private var notificationJob: Job? = null

    fun loadNotifications(userId: String) {
        // Cancelar job anterior si existe
        notificationJob?.cancel()

        notificationJob = viewModelScope.launch {
            try {
                notificationRepository.getNotificationsForUser(userId).collect { notifications ->
                    _state.value = _state.value.copy(notifications = notifications)
                }
            } catch (e: Exception) {
                // Ignorar errores de permisos al cerrar sesión
                if (!e.message.orEmpty().contains("PERMISSION_DENIED")) {
                    _state.value = _state.value.copy(error = e.message)
                }
            }
        }
    }

    fun stopListening() {
        notificationJob?.cancel()
        notificationJob = null
        _state.value = NotificationState()
    }

    fun loadUsers() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            authRepository.getAllUsers().fold(
                onSuccess = { users ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        users = users
                    )
                },
                onFailure = { e ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            )
        }
    }

    fun toggleUserSelection(userId: String) {
        val currentSelection = _state.value.selectedUsers.toMutableSet()
        if (currentSelection.contains(userId)) {
            currentSelection.remove(userId)
        } else {
            currentSelection.add(userId)
        }
        _state.value = _state.value.copy(selectedUsers = currentSelection)
    }

    fun selectAllUsers() {
        val allUserIds = _state.value.users.map { it.uid }.toSet()
        _state.value = _state.value.copy(selectedUsers = allUserIds)
    }

    fun clearSelection() {
        _state.value = _state.value.copy(selectedUsers = emptySet())
    }

    fun sendNotification(
        title: String,
        body: String,
        senderId: String,
        senderName: String
    ) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, sendSuccess = false, error = null)

            val recipientIds = _state.value.selectedUsers.toList()
            if (recipientIds.isEmpty()) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Debe seleccionar al menos un usuario"
                )
                return@launch
            }

            notificationRepository.sendNotificationToMultipleUsers(
                title = title,
                body = body,
                senderId = senderId,
                senderName = senderName,
                recipientIds = recipientIds
            ).fold(
                onSuccess = {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        sendSuccess = true,
                        selectedUsers = emptySet()
                    )
                },
                onFailure = { e ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            )
        }
    }

    fun markAsRead(notificationId: String) {
        viewModelScope.launch {
            notificationRepository.markAsRead(notificationId)
        }
    }

    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }

    fun clearSendSuccess() {
        _state.value = _state.value.copy(sendSuccess = false)
    }
}