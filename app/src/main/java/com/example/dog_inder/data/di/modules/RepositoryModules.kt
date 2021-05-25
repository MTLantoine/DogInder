package com.example.dog_inder.data.di.modules

import com.example.dog_inder.data.dataSources.remote.firebase.FirebaseAuthService
import com.example.dog_inder.data.repositories.AuthRepository
import org.koin.dsl.module

object RepositoryModules {

    val repositories = module {
        fun createAuthRepositoty(firebaseAuthService: FirebaseAuthService) = AuthRepository(firebaseAuthService )

        single { createAuthRepositoty(get()) }
    }

}