package com.moustafa.githubrepos

import android.app.Application
import com.moustafa.githubrepos.di.dbModule
import com.moustafa.githubrepos.di.networkModule
import com.moustafa.githubrepos.di.repoModule
import com.moustafa.githubrepos.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ReposApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@ReposApplication)
            modules(dbModule, networkModule, repoModule, viewModelModule)
        }
    }

}