package com.moustafa.githubrepos.di

import android.content.Context
import com.moustafa.githubrepos.data.db.RepositoriesRoomDB
import org.koin.dsl.module

val dbModule = module {
    single {
        RepositoriesRoomDB(get() as Context)
    }
}

