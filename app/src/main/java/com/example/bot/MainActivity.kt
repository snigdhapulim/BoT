package com.example.bot

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.bot.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        val acco = GoogleSignIn.getLastSignedInAccount(this)
        Log.i("Main Activity", "The token already existing is " + acco?.serverAuthCode)
    }
}
