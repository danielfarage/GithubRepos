package com.daniel.farage.githubrepos.domain.usecase

import com.daniel.farage.githubrepos.domain.repositories.GithubRepository

class GetTotalReposUseCase(private val repository: GithubRepository) {

    suspend fun execute() = repository.getTotalRepository()

}