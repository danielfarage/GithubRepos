package com.daniel.farage.githubrepos.util.di

import com.daniel.farage.githubrepos.data.remote.GithubApi
import com.daniel.farage.githubrepos.data.remote.GithubRepositoryImpl
import com.daniel.farage.githubrepos.domain.repositories.GithubRepository
import com.daniel.farage.githubrepos.domain.usecase.GetRepositoriesUseCase
import com.daniel.farage.githubrepos.util.Constants
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Modules {
    val dataModule: Module = module {
        single<GithubApi> {
            Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(GithubApi::class.java)
        }
        single<GithubRepository>{ GithubRepositoryImpl(get()) }
    }
    val domainModule: Module = module {
        factory { GetRepositoriesUseCase(get()) }
    }
    val featuresModule: Module = module {  }
}