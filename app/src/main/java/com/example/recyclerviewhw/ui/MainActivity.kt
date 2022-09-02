package com.example.recyclerviewhw.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.recyclerviewhw.R
import com.example.recyclerviewhw.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       // val viewPager = findViewById<ViewPager>(R.id.viewpager)
       // val adapter = SimpleFragmentPagerAdapter(supportFragmentManager)
       // viewPager.adapter = adapter
    }
}