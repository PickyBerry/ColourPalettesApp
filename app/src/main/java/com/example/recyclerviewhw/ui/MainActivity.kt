package com.example.recyclerviewhw.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerviewhw.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

//This is a single-activity app
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}