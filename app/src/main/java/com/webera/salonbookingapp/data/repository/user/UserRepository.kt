package com.webera.salonbookingapp.data.repository.user

import com.webera.salonbookingapp.data.model.User
import com.webera.salonbookingapp.data.remote.firestore.FirestoreService
import kotlinx.coroutines.tasks.await

class UserRepository(
    private val firestoreService: FirestoreService = FirestoreService()
) {

    suspend fun saveUser(user: User): Result<Unit> {
        return try {
            firestoreService
                .usersCollection
                .document(user.uid)
                .set(user)
                .await()

            Result.success(Unit)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    suspend fun getUser(uid: String): Result<User> {
        return try {
            val document = firestoreService
                .usersCollection
                .document(uid)
                .get()
                .await()

            val user = document.toObject(User::class.java)

            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(
                    NoSuchElementException("User not found.")
                )
            }
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}