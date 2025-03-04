package com.nexa.cinemate.data.di

import androidx.room.Room
import com.nexa.cinemate.data.database.NexaDataBase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val storageModule = module {
    single {
       Room.databaseBuilder(
           androidContext(),
           NexaDataBase::class.java, name = "nexa-database.db"
       ).build()
    }
    single {
        get<NexaDataBase>().movieDao()
    }

}