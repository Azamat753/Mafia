package com.lawlett.mafia

import android.app.Application
import com.lawlett.mafia.di.databaseModule
import com.lawlett.mafia.di.networkModule
import com.lawlett.mafia.di.repositoryModule
import com.lawlett.mafia.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            androidFileProperties()

            modules(listOf(viewModelModule, databaseModule, networkModule, repositoryModule))
        }
    }
}