package com.daniel.farage.githubrepos.features.repolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.daniel.farage.githubrepos.databinding.FragmentRepolistBinding

class RepoListFragment: Fragment() {

    private lateinit var binding: FragmentRepolistBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepolistBinding.inflate(inflater, container, false)
        return binding.root
    }

}