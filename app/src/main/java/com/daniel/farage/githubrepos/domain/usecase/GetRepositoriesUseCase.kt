package com.daniel.farage.githubrepos.domain.usecase

import com.daniel.farage.githubrepos.domain.model.RepositoryData
import com.daniel.farage.githubrepos.domain.repositories.GithubRepository
import com.daniel.farage.githubrepos.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetRepositoriesUseCase(val githubRepository: GithubRepository) {

    suspend fun execute(page: Int) : Resource<RepositoryData> = withContext(Dispatchers.IO) {
        githubRepository.getRepository(page)
    }

}