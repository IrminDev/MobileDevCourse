package com.github.irmin.firebaseapp.data.model

import com.google.firebase.firestore.PropertyName

data class User(
    val uid: String = "",
    val email: String = "",
    val displayName: String = "",
    @PropertyName("admin")
    val isAdmin: Boolean = false,
    val fcmToken: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

