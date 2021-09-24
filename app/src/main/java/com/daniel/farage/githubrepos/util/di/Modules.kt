package com.daniel.farage.githubrepos.util.di

import com.daniel.farage.githubrepos.data.remote.GithubApi
import com.daniel.farage.githubrepos.data.remote.GithubPageSource
import com.daniel.farage.githubrepos.data.remote.GithubRepositoryImpl
import com.daniel.farage.githubrepos.domain.repositories.GithubRepository
import com.daniel.farage.githubrepos.domain.usecase.GetRepositoriesUseCase
import com.daniel.farage.githubrepos.domain.usecase.GetTotalReposUseCase
import com.daniel.farage.githubrepos.features.repolist.RepoListViewModel
import com.daniel.farage.githubrepos.util.Constants
import org.koin.androidx.viewmodel.dsl.viewModel
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
        single { GithubPageSource(get()) }
    }
    val domainModule: Module = module {
        single<GithubRepository> { GithubRepositoryImpl(get(), get()) }
        factory { GetRepositoriesUseCase(get()) }
        factory { GetTotalReposUseCase(get()) }
    }
    val featuresModule: Module = module {
        viewModel { RepoListViewModel(get(), get()) }
    }
}