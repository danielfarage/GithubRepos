package com.daniel.farage.githubrepos.features.repolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDestination
import androidx.navigation.NavDirections
import com.daniel.farage.githubrepos.domain.model.Repository
import com.daniel.farage.githubrepos.domain.usecase.GetRepositoriesUseCase
import com.daniel.farage.githubrepos.domain.usecase.GetTotalReposUseCase
import com.daniel.farage.githubrepos.util.Resource
import com.daniel.farage.githubrepos.util.ViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RepoListViewModel(
    private val useCase: GetRepositoriesUseCase,
    private val totalReposUseCase: GetTotalReposUseCase
) : ViewModel() {

    private val _totalRepos = MutableLiveData<Int>()
    val totalRepos: LiveData<Int> = _totalRepos

    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading(true))
    val viewState = _viewState.asStateFlow()

    fun getTotalRepositories() {
        viewModelScope.launch {
            when(val result = totalReposUseCase.execute()) {
                is Resource.Failure -> {
                    _viewState.emit(ViewState.Error(result.data))
                }
                is Resource.Success -> {
                    _totalRepos.postValue(result.data.totalRepos)
                    _viewState.emit(ViewState.Loading(false))
                }
            }
        }
    }

    fun navigateTo(direction: Int) {
        _viewState.tryEmit(ViewState.Navigate(direction))
    }

    suspend fun getReposityList() = useCase.execute()

}