package com.daniel.farage.githubrepos.data.remote

import com.daniel.farage.githubrepos.data.model.RepositoryListResponse
import com.daniel.farage.githubrepos.util.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    @GET("/repositories")
    suspend fun getRepositories(
        @Query(Constants.Params.QUERY_LANGUAGE)
        language: String = Constants.Params.QUERY_LANGUAGE_VALUE,
        @Query(Constants.Params.QUERY_SORT)
        sort: String = Constants.Params.QUERY_SORT_VALUE,
        @Query(Constants.Params.QUERY_PAGE)
        page: Int = 1
    ): RepositoryListResponse

}