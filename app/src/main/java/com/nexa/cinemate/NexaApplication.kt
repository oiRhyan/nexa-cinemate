package com.nexa.cinemate

import android.app.Application
import com.nexa.cinemate.data.di.apiModule
import com.nexa.cinemate.data.di.repositoryModule
import com.nexa.cinemate.data.di.storageModule
import com.nexa.cinemate.data.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NexaApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@NexaApplication)
            modules(apiModule, repositoryModule, viewModelModule, storageModule)
        }
    }

}