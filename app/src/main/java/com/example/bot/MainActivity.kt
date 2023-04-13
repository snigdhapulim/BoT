package com.example.bot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.bot.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var card:CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        card = findViewById(R.id.signup)
        card.setOnClickListener{
            Toast.makeText(this, "buttom clicked", Toast.LENGTH_SHORT).show()
            val intent  = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
//            finish()


        }
    }
}