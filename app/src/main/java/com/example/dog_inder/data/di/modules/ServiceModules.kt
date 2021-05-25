package com.example.dog_inder.data.di.modules

import com.example.dog_inder.data.dataSources.remote.firebase.FirebaseAuthService
import org.koin.dsl.module

object ServiceModules {

    val services = module {
        fun createFirebaseAuthService() = FirebaseAuthService();

        single {
            createFirebaseAuthService()
        }
    }
}