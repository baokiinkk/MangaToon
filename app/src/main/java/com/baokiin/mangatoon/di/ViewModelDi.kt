package com.baokiin.mangatoon.di

import com.baokiin.mangatoon.ui.activity.LoginViewModel
import com.baokiin.mangatoon.ui.chapterdetail.DetailChapterViewModel
import com.baokiin.mangatoon.ui.detail.DetailViewModel
import com.baokiin.mangatoon.ui.detailgener.DetailGenerViewModel
import com.baokiin.mangatoon.ui.genre.GenreViewModel
import com.baokiin.mangatoon.ui.home.HomeViewModel
import com.baokiin.mangatoon.ui.library.LibraryViewModel
import com.baokiin.mangatoon.ui.mine.MineViewModel
import com.baokiin.mangatoon.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

// File này ta sẽ tạo ra các module của tầng ViewModel

val blankViewmodelDi: Module = module {
    viewModel { HomeViewModel(get()) }
    viewModel { GenreViewModel(get()) }
    viewModel { DetailGenerViewModel(get()) }
    viewModel { DetailViewModel(get(),get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { LibraryViewModel(get()) }
    viewModel { MineViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { DetailChapterViewModel(get(),get()) }
}
