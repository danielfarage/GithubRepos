package com.daniel.farage.githubrepos.data.remote

import com.daniel.farage.githubrepos.data.model.mapToRepositoryData
import com.daniel.farage.githubrepos.domain.model.RepositoryData
import com.daniel.farage.githubrepos.domain.repositories.GithubRepository
import com.daniel.farage.githubrepos.util.Resource
import java.lang.Exception

class GithubRepositoryImpl(val api: GithubApi): GithubRepository {

    override suspend fun getRepository(page: Int): Resource<RepositoryData> = try {
        val response = api.getRepositories(page = page)
        Resource.Success(response.mapToRepositoryData())
    } catch (exception: Exception) {
        Resource.Failure(exception)
    }
}