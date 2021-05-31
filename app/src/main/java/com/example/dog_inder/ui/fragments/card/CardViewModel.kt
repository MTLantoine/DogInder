package com.example.dog_inder.ui.fragments.card

import androidx.lifecycle.ViewModel
import com.example.dog_inder.data.repositories.AuthRepository

class CardViewModel(val authRepository: AuthRepository) : ViewModel() {

    fun getCurrentUser() = authRepository.getCurrentFirebaseUser()
}