package com.daniel.farage.githubrepos.data.model

import com.daniel.farage.githubrepos.domain.model.Repository
import com.daniel.farage.githubrepos.domain.model.RepositoryData
import com.google.gson.annotations.SerializedName

data class RepositoryListResponse(

    @SerializedName("total_count")
    val totalRepositories: Int?,
    @SerializedName("items")
    val repositories: List<RepositoryResponse>?
)

fun RepositoryListResponse.mapToRepositoryData(): RepositoryData {
    val repos = this.repositories ?: emptyList()
    return RepositoryData(
        this.totalRepositories ?: 0,
        repos.map { it.mapToRepository() }
    )
}