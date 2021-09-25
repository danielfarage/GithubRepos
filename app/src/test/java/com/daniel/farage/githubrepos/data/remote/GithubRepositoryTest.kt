package com.daniel.farage.githubrepos.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.daniel.farage.githubrepos.data.model.RepositoryListResponse
import com.daniel.farage.githubrepos.data.model.mapToRepositoryData
import com.daniel.farage.githubrepos.domain.model.Repository
import com.daniel.farage.githubrepos.domain.repositories.GithubRepository
import com.daniel.farage.githubrepos.util.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals
import kotlin.test.assertTrue


@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class GithubRepositoryTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val api = mockk<GithubApi>(relaxed = true)
    private val pageSource = mockk<GithubPageSource>(relaxed = true)
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private lateinit var githubRepository: GithubRepository

    @Before
    fun setUp() {
        githubRepository = GithubRepositoryImpl(api, pageSource)
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testCoroutineDispatcher.cancel()
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun getRepository_shouldReturnSuccess() = runBlockingTest {
        val mockApiResponse = mockk<RepositoryListResponse>(relaxed = true)
        val mockLoadResult = mockApiResponse.mapToRepositoryData().repoList

        val result = mutableListOf<PagingData<Repository>>()

        coEvery { api.getRepositories() } returns mockApiResponse
        coEvery { pageSource.load(any()) } returns PagingSource.LoadResult.Page(
            mockLoadResult,
            1,
            2
        )
        val job = launch {
            githubRepository.getRepository().collect { result.add(it) }
        }

        job.cancel()
        assertNotNull(result.first())

    }

    @Test
    fun getRepository_shouldReturnFailure() = runBlockingTest {
        val result = mutableListOf<PagingData<Repository>>()

        coEvery { api.getRepositories() } throws mockk<Exception>(relaxed = true)
        val job = launch {
            githubRepository.getRepository().collect { result.add(it) }
        }

        job.cancel()
        assertNotNull(result.first())
    }

    @Test
    fun getTotalRepositoryshouldReturnSuccess() = runBlockingTest {
        val mockResponse = mockk<RepositoryListResponse>(relaxed = true)

        coEvery { api.getTotalRepositories() } returns mockResponse
        val result = githubRepository.getTotalRepository()

        assertTrue(result is Resource.Success)
        assertEquals(mockResponse.mapToRepositoryData(), result.data)
    }

    @Test
    fun getTotalRepositoryshouldReturnFailure() = runBlockingTest {
        val mockResponse = mockk<java.lang.Exception>(relaxed = true)

        coEvery { api.getTotalRepositories() } throws mockResponse
        val result = githubRepository.getTotalRepository()

        assertTrue(result is Resource.Failure)
        assertEquals(mockResponse, result.data)
    }
}