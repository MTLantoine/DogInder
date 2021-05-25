package com.example.dog_inder.data.di.modules

import com.example.dog_inder.data.repositories.AuthRepository
import com.example.dog_inder.ui.home.HomeViewModel
import org.koin.dsl.module

object ViewModelModules {

    val viewModels = module {

        fun createHomeViewModel(authRepository: AuthRepository) = HomeViewModel(authRepository)

        single {createHomeViewModel((get()))}
    }
}