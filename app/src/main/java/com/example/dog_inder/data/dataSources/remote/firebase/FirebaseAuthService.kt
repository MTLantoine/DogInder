package com.example.dog_inder.data.dataSources.remote.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseAuthService {

    private var auth: FirebaseAuth = Firebase.auth

    fun getCurrentFirebaseUser() = auth.currentUser

    fun signIn(email: String, password: String) = auth.signInWithEmailAndPassword(email, password)

    fun signUp(email: String, password: String) = auth.createUserWithEmailAndPassword(email, password)
}