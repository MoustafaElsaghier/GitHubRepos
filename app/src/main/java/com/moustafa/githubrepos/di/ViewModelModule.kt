package com.moustafa.githubrepos.di

import com.moustafa.githubrepos.ui.features.repos.user_uepos_list.viewmodel.UserRepositoriesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        UserRepositoriesViewModel(get())
    }
}