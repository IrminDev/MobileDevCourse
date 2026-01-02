package com.github.irmin.firebaseapp.data.model

data class Notification(
    val id: String = "",
    val title: String = "",
    val body: String = "",
    val senderId: String = "",
    val senderName: String = "",
    val recipientId: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false
)

