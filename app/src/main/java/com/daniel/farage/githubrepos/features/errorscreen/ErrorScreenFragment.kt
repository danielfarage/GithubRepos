package com.daniel.farage.githubrepos.features.errorscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.daniel.farage.githubrepos.databinding.FragmentErrorScreenBinding

class ErrorScreenFragment : Fragment() {

    private lateinit var binding: FragmentErrorScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentErrorScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButton()
    }

    private fun setupButton() {
        binding.buttonTryAgain.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}