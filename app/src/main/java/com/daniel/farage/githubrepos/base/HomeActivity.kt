package com.daniel.farage.githubrepos.base

import android.app.Activity
import android.os.Bundle
import com.daniel.farage.githubrepos.databinding.ActivityHomeBinding

class HomeActivity: Activity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}