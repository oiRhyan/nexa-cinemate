package com.nexa.cinemate.data.di

import com.nexa.cinemate.screens.ui.views.anime.AnimeViewModel
import com.nexa.cinemate.screens.ui.views.home.HomeViewModel
import com.nexa.cinemate.screens.ui.views.login.LoginViewModel
import com.nexa.cinemate.screens.ui.views.serie.SeriesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        HomeViewModel(get())
    }

    viewModel {
        AnimeViewModel(get())
    }

    viewModel {
        SeriesViewModel(get())
    }

    viewModel {
        LoginViewModel()
    }

}