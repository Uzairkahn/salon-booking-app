package com.webera.salonbookingapp.data.remote.firestore

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreService {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    val usersCollection: CollectionReference
        get() = firestore.collection(USERS_COLLECTION)

    companion object {
        private const val USERS_COLLECTION = "users"
    }
}