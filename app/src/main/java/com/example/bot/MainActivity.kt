package com.example.bot

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bot.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.forEventsActivity.setOnClickListener {
            //val intent = Intent(this, )
            //startActivity(intent)
        }
        binding.forLogin.setOnClickListener {
            //val intent = Intent(this, )
            //startActivity(intent)
        }
    }
}