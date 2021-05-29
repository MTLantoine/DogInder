package com.example.dog_inder.data.repositories

import androidx.lifecycle.MutableLiveData
import com.example.dog_inder.data.dataSources.remote.firebase.FirebaseAuthService
import com.google.firebase.auth.FirebaseUser

class AuthRepository(val firebaseAuthService: FirebaseAuthService) {

    fun getCurrentFirebaseUser() = firebaseAuthService.getCurrentFirebaseUser()

    fun signIn(email: String, password: String) : MutableLiveData<FirebaseUser?> {
        val response = MutableLiveData<FirebaseUser?>()
        firebaseAuthService.signIn(email, password)
            .addOnSuccessListener {
                response.value = it.user
            }
            .addOnFailureListener {
                
            }

        return response
    }

    fun signUp(email: String, password: String) : MutableLiveData<FirebaseUser?> {
        val response = MutableLiveData<FirebaseUser?>()
        firebaseAuthService.signUp(email, password)
            .addOnSuccessListener {
                response.value = it.user
            }
            .addOnFailureListener{

            }

        return response
    }
}