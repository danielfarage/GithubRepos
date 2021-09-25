package com.daniel.farage.githubrepos.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.daniel.farage.githubrepos.data.model.mapToRepositoryData
import com.daniel.farage.githubrepos.domain.model.Repository

class GithubPageSource(private val githubApi: GithubApi) :
    PagingSource<Int, Repository>() {
    override fun getRefreshKey(state: PagingState<Int, Repository>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> = try {
        val position = params.key ?: 1
        val response = githubApi.getRepositories(page = position)
        val prevKey = if (position == 1) null else position
        val nextKey = if (response.repositories.isNullOrEmpty()) null else position + 1
        val resouce = response.mapToRepositoryData()
        LoadResult.Page(resouce.repoList, prevKey, nextKey)
    } catch (exception: Exception) {
        LoadResult.Error(exception)
    }
}
