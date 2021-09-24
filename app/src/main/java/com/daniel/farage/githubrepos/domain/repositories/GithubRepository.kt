package com.daniel.farage.githubrepos.domain.repositories

import androidx.paging.PagingData
import com.daniel.farage.githubrepos.data.model.RepositoryListResponse
import com.daniel.farage.githubrepos.domain.model.Repository
import com.daniel.farage.githubrepos.domain.model.RepositoryData
import com.daniel.farage.githubrepos.util.Resource
import kotlinx.coroutines.flow.Flow

interface GithubRepository {

    suspend fun getRepository(): Flow<PagingData<Repository>>

    suspend fun getTotalRepository(): Resource<RepositoryData>

}