package com.github.irmin.firebaseapp.data.repository

import com.github.irmin.firebaseapp.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val usersCollection = firestore.collection("users")

    // Contraseña maestra para crear administradores
    companion object {
        const val ADMIN_MASTER_PASSWORD = "Admin@2024#SecretKey"
    }

    val currentUser: FirebaseUser?
        get() = auth.currentUser

    suspend fun signIn(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user?.let {
                Result.success(it)
            } ?: Result.failure(Exception("Usuario no encontrado"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signUp(
        email: String,
        password: String,
        displayName: String,
        isAdmin: Boolean = false,
        adminPassword: String? = null
    ): Result<FirebaseUser> {
        return try {
            // Verificar contraseña maestra si se intenta crear un admin
            if (isAdmin && adminPassword != ADMIN_MASTER_PASSWORD) {
                return Result.failure(Exception("Contraseña maestra incorrecta"))
            }

            val result = auth.createUserWithEmailAndPassword(email, password).await()
            result.user?.let { firebaseUser ->
                // Crear documento de usuario en Firestore
                val user = User(
                    uid = firebaseUser.uid,
                    email = email,
                    displayName = displayName,
                    isAdmin = isAdmin,
                    createdAt = System.currentTimeMillis()
                )
                usersCollection.document(firebaseUser.uid).set(user).await()
                Result.success(firebaseUser)
            } ?: Result.failure(Exception("Error al crear usuario"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserData(uid: String): Result<User> {
        return try {
            val document = usersCollection.document(uid).get().await()
            document.toObject(User::class.java)?.let {
                Result.success(it)
            } ?: Result.failure(Exception("Usuario no encontrado"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateUserData(user: User): Result<Unit> {
        return try {
            usersCollection.document(user.uid).set(user).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateFcmToken(uid: String, token: String): Result<Unit> {
        return try {
            usersCollection.document(uid).update("fcmToken", token).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAllUsers(): Result<List<User>> {
        return try {
            val snapshot = usersCollection.get().await()
            val users = snapshot.documents.mapNotNull { it.toObject(User::class.java) }
            Result.success(users)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun signOut() {
        auth.signOut()
    }
}

