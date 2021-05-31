package com.example.dog_inder.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dog_inder.data.repositories.AuthRepository

class HomeViewModel(val authRepository: AuthRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

    val text: LiveData<String> = _text

    fun signIn(email: String, password: String) = authRepository.signIn(email, password)

    fun signUp(email: String, password: String) = authRepository.signUp(email, password)
}