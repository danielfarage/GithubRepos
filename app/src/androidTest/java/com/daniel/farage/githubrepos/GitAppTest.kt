package com.daniel.farage.githubrepos

import android.app.Application
import com.daniel.farage.githubrepos.data.remote.GithubApi
import com.daniel.farage.githubrepos.data.remote.GithubPageSource
import com.daniel.farage.githubrepos.util.di.Modules
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GitAppTest: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(mockApi, Modules.domainModule, Modules.featuresModule))
            androidContext(this@GitAppTest)
            androidLogger()
        }
    }

    private val mockApi = module {
        single<GithubApi> {
            Retrofit.Builder()
                .baseUrl("http://localhost:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpProvider)
                .build().create(GithubApi::class.java)
        }
        single { GithubPageSource(get()) }
    }

    companion object {
        val okhttpProvider by lazy { OkHttpClient.Builder()
            .build() }
    }

}