package com.daniel.farage.githubrepos.data.model

import com.daniel.farage.githubrepos.domain.model.Repository
import com.google.gson.annotations.SerializedName

data class RepositoryResponse(
    @SerializedName("full_name")
    val nameRepo: String?,
    @SerializedName("stargazers_count")
    val stars: Int?,
    @SerializedName("forks_count")
    val forkCount: Int?,
    @SerializedName("owner")
    val ownerRepository: OwnerRepositoryResponse?
)

data class OwnerRepositoryResponse(
    @SerializedName("avatar_url")
    val authorPic: String?,
    @SerializedName("login")
    val authorName: String?
)

fun RepositoryResponse.mapToRepository() : Repository {
    return Repository(
        this.nameRepo ?: "",
        this.stars ?: 0,
        this.forkCount ?: 0,
        this.ownerRepository?.authorPic  ?: "",
        this.ownerRepository?.authorName  ?: ""
    )
}
