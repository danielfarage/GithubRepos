package com.daniel.farage.githubrepos.domain.usecase

import com.daniel.farage.githubrepos.domain.repositories.GithubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetRepositoriesUseCase(private val githubRepository: GithubRepository) {

    suspend fun execute() = withContext(Dispatchers.IO) {
        githubRepository.getRepository()
    }

}