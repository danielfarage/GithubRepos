package com.daniel.farage.githubrepos.features.repolist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.daniel.farage.githubrepos.domain.model.Repository
import com.daniel.farage.githubrepos.domain.model.RepositoryData
import com.daniel.farage.githubrepos.domain.usecase.GetRepositoriesUseCase
import com.daniel.farage.githubrepos.domain.usecase.GetTotalReposUseCase
import com.daniel.farage.githubrepos.util.Resource
import com.daniel.farage.githubrepos.util.ViewState
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class RepoListViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val totalUseCase: GetTotalReposUseCase = mockk(relaxed = true)
    private val reposUseCase: GetRepositoriesUseCase = mockk(relaxed = true)
    private lateinit var viewModel: RepoListViewModel
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testCoroutineDispatcher)
        viewModel = RepoListViewModel(reposUseCase, totalUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testCoroutineDispatcher.cancel()
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun getTotalRepositories_shouldReturnSuccess() = runBlockingTest {
        //given
        val mockObserver = mockk<Observer<Int>>(relaxed = true)
        val slot = slot<Int>()
        val result = Resource.Success<RepositoryData>(mockk(relaxed = true))

        //when
        viewModel.totalRepos.observeForever(mockObserver)
        coEvery { totalUseCase.execute() } returns result
        viewModel.getTotalRepositories()

        //then
        verify(exactly = 1) { mockObserver.onChanged(capture(slot)) }
        assertEquals(slot.captured, result.data.totalRepos)
    }

    @Test
    fun getTotalRepositories_shouldReturnFailure() = runBlockingTest {
        //given
        val mockObserver = mockk<Observer<Int>>(relaxed = true)
        val result = Resource.Failure(mockk(relaxed = true))
        val expect = ViewState.Error(result.data)

        //when
        viewModel.totalRepos.observeForever(mockObserver)
        coEvery { totalUseCase.execute() } returns result
        viewModel.getTotalRepositories()

        //then
        assertEquals(viewModel.viewState.value, expect)
        verify(exactly = 0) { mockObserver.onChanged(any()) }
    }

    @Test
    fun initViewModel_viewStateShouldBeLoadingTrue() {
        //given
        val expected = ViewState.Loading(true)

        //then
        assertEquals(expected, viewModel.viewState.value)
    }

    @Test
    fun callNavigation_shouldUpdateViewState() {
        //given
        val expected = ViewState.Navigate(0)

        //when
        viewModel.navigateTo(0)

        //then
        assertEquals(expected, viewModel.viewState.value)
    }

    @Test
    fun callGetReposityList_shouldReturnList() = runBlockingTest {
        val expected: PagingData<Repository> = mockk()
        val response = flow { emit(expected) }
        val result = mutableListOf<PagingData<Repository>>()

        coEvery { reposUseCase.execute() } returns response
        viewModel.getReposityList().collectLatest { result.add(it) }

        assertEquals(expected, result.first())
    }

}