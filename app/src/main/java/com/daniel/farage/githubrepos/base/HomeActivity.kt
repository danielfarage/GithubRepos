package com.daniel.farage.githubrepos.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.daniel.farage.githubrepos.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}