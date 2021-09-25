package com.daniel.farage.githubrepos.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.daniel.farage.githubrepos.data.model.RepositoryListResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class GithubPageSourceTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    private val api = mockk<GithubApi>(relaxed = true)
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private lateinit var pageSource: GithubPageSource

    @Before
    fun setUp() {
        pageSource = GithubPageSource(api)
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testCoroutineDispatcher.cancel()
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun callLoad_shouldReturnSuccessPage() = runBlockingTest {
        //given
        val mockReponse = mockk<RepositoryListResponse>(relaxed = true)
        val param = mockk<PagingSource.LoadParams<Int>>(relaxed = true)

        //when
        coEvery { param.key } returns 1
        coEvery { api.getRepositories() } returns mockReponse
        val result = pageSource.load(param)

        //then
        assertTrue(result is PagingSource.LoadResult.Page)
    }

    @Test
    fun callLoad_shouldReturnError() = runBlockingTest {
        //given
        val param = mockk<PagingSource.LoadParams<Int>>(relaxed = true)

        //when
        coEvery { param.key } returns 1
        coEvery { api.getRepositories() } throws mockk<Exception>()
        val result = pageSource.load(param)

        //then
        assertTrue(result is PagingSource.LoadResult.Error)
    }
}