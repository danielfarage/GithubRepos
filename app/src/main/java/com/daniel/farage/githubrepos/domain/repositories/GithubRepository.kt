package com.daniel.farage.githubrepos.domain.repositories

import com.daniel.farage.githubrepos.domain.model.RepositoryData
import com.daniel.farage.githubrepos.util.Resource

interface GithubRepository {

    suspend fun getRepository(page: Int) : Resource<RepositoryData>

}