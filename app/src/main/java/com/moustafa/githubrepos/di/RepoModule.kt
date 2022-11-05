package com.moustafa.githubrepos.di

import com.moustafa.githubrepos.data.repo.RepositoriesRemoteMediator
import com.moustafa.githubrepos.data.repo.GetRepositoriesRxRepository
import org.koin.dsl.module

val repoModule = module {
    single { GetRepositoriesRxRepository(get(), get()) }
    single { RepositoriesRemoteMediator(get(), get()) }
}

