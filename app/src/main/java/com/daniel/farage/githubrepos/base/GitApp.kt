package com.daniel.farage.githubrepos.base

import android.app.Application
import com.daniel.farage.githubrepos.util.di.Modules.dataModule
import com.daniel.farage.githubrepos.util.di.Modules.domainModule
import com.daniel.farage.githubrepos.util.di.Modules.featuresModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GitApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@GitApp)
            modules(listOf(dataModule, featuresModule, domainModule))
        }
    }
}