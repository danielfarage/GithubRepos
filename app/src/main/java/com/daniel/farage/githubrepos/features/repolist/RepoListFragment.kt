package com.daniel.farage.githubrepos.features.repolist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.daniel.farage.githubrepos.R
import com.daniel.farage.githubrepos.databinding.FragmentRepolistBinding
import com.daniel.farage.githubrepos.util.ViewState
import com.daniel.farage.githubrepos.util.extension.isVisible
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepoListFragment : Fragment() {

    private val viewModel: RepoListViewModel by viewModel()

    private lateinit var binding: FragmentRepolistBinding
    private val repoAdapter: RepoAdapter by lazy { RepoAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepolistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        fetchRepositories()
        fetchTotalRepositories()
        collectViewState()
        viewModel.totalRepos.observe(viewLifecycleOwner, observeTotalRepositories())
    }

    private fun setupView() {
        binding.recyclerViewRepoList.apply {
            adapter = repoAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun collectViewState() = lifecycleScope.launchWhenStarted {
        viewModel.viewState.collect { state ->
            when (state) {
                is ViewState.Error -> {
                    viewModel.navigateTo(R.id.action_repoListFragment_to_errorScreenFragment)
                }
                is ViewState.Loading -> {
                    binding.progressBarLoad.isVisible(state.isLoading)
                }
                is ViewState.Navigate -> {
                    try {
                        findNavController().navigate(state.toDestination)
                    } catch (exception: Exception) {
                        exception.message?.let {
                            Log.d(
                                this@RepoListFragment.javaClass.simpleName,
                                it
                            )
                        }
                    }
                }
            }
        }
    }

    private fun observeTotalRepositories() = Observer<Int> { total ->
        binding.textViewSubtitleFounds.text =
            getString(R.string.total_encontrados, total.toString())
    }

    private fun fetchTotalRepositories() {
        viewModel.getTotalRepositories()
    }

    private fun fetchRepositories() = lifecycleScope.launch {
        viewModel.getReposityList().collectLatest {
            repoAdapter.submitData(it)
        }
    }

}