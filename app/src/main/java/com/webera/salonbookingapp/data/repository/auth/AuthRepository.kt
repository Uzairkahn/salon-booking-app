package com.webera.salonbookingapp.data.repository.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthRepository {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun signup(
        email: String,
        password: String
    ): Result<FirebaseUser?> {
        return try {
            val result = firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .await()

            Result.success(result.user)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    suspend fun login(
        email: String,
        password: String
    ): Result<FirebaseUser?> {
        return try {
            val result = firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .await()

            Result.success(result.user)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    fun logout() {
        firebaseAuth.signOut()
    }

    fun currentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }
}