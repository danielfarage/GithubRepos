package com.daniel.farage.githubrepos.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.daniel.farage.githubrepos.data.model.mapToRepositoryData
import com.daniel.farage.githubrepos.domain.model.Repository
import com.daniel.farage.githubrepos.domain.model.RepositoryData
import com.daniel.farage.githubrepos.domain.repositories.GithubRepository
import com.daniel.farage.githubrepos.util.Resource
import kotlinx.coroutines.flow.Flow

class GithubRepositoryImpl(
    private val api: GithubApi,
    private val pageSource: GithubPageSource
) : GithubRepository {

    override suspend fun getRepository(): Flow<PagingData<Repository>> {
        return Pager(
            PagingConfig(pageSize = 10, enablePlaceholders = true)
        ) {
            pageSource
        }.flow
    }

    override suspend fun getTotalRepository(): Resource<RepositoryData> = try {
        val response = api.getTotalRepositories().mapToRepositoryData()
        Resource.Success(response)
    } catch (exception: java.lang.Exception) {
        Resource.Failure(exception)
    }
}