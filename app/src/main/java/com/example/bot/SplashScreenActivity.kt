package com.example.bot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val intent = getIntent()
        val isFirst = intent.getBooleanExtra("first", true)
        val isMain = intent.getBooleanExtra("main", false)

        val animation = AnimationUtils.loadAnimation(this, R.anim.splash_logo_animation)

        // Apply the animation to the ImageView
        val splashLogo = findViewById<ImageView>(R.id.splash_logo)
        splashLogo.startAnimation(animation)

        if(isFirst) {
            // Start the main activity after a delay
            Handler().postDelayed({
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        }else if(isMain){
            Handler().postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)
            finish()
        }
    }
}