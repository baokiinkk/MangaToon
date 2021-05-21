package com.baokiin.mangatoon.di

import com.baokiin.mangatoon.ui.genre.GenreViewModel
import com.baokiin.mangatoon.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

// File này ta sẽ tạo ra các module của tầng ViewModel

val blankViewmodelDi: Module = module {
    viewModel { HomeViewModel(get()) }
    viewModel { GenreViewModel(get()) }
}
