package com.example.dog_inder.data.di

import android.app.Application
import com.example.dog_inder.data.di.modules.RepositoryModules
import com.example.dog_inder.data.di.modules.ServiceModules
import com.example.dog_inder.data.di.modules.ViewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@BaseApplication)
            modules(
                listOf(
                    ServiceModules.services,
                    RepositoryModules.repositories,
                    ViewModelModules.viewModels

                )
            )
            koin.createRootScope()
        }
    }
}