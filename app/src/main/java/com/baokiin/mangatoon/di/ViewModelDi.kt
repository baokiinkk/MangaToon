package com.baokiin.mangatoon.di

import com.baokiin.mangatoon.ui.chapterdetail.DetailChapterViewModel
import com.baokiin.mangatoon.ui.detail.DetailViewModel
import com.baokiin.mangatoon.ui.detailgener.DetailGenerViewModel
import com.baokiin.mangatoon.ui.genre.GenreViewModel
import com.baokiin.mangatoon.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

// File này ta sẽ tạo ra các module của tầng ViewModel

val blankViewmodelDi: Module = module {
    viewModel { HomeViewModel(get()) }
    viewModel { GenreViewModel(get()) }
    viewModel { DetailGenerViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { DetailChapterViewModel(get()) }
}
