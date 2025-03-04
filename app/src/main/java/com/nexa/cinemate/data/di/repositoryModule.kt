package com.nexa.cinemate.data.di

import com.nexa.cinemate.data.repositories.AnimeRepository
import com.nexa.cinemate.data.repositories.MovieRepository
import com.nexa.cinemate.data.repositories.SeriesRepository
import org.koin.dsl.module

val repositoryModule = module {

    single{
        MovieRepository(get(), get())
    }

    single {
        AnimeRepository(get())
    }

    single {
        SeriesRepository(get())
    }
}
