package com.example.bot.activities

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bot.fragments.EventListFragment
import com.example.bot.R
import com.example.bot.databinding.ActivityMainBinding
import com.example.bot.network.UserAPI
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onDestroy() {
        super.onDestroy()
        // cancel all coroutines when activity is destroyed
        CoroutineScope(Dispatchers.Main).cancel()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val eventListFragment = EventListFragment()
        val args = Bundle()
        args.putString("filter", "today")
        eventListFragment.arguments = args

        supportFragmentManager.beginTransaction()
            .replace(R.id.events_view, eventListFragment)
            .commit()

        val acco = GoogleSignIn.getLastSignedInAccount(this)
        CoroutineScope(Dispatchers.Main).launch {
            try {
                var loggedIn =
                    UserAPI.RefreshTokenApi.retrofitRefreshTokenService.refreshToken(acco?.email.toString())

                Log.i("Main Activity", "User tokens are ${loggedIn.access_token} : ${loggedIn.refresh_token}")
            } catch (e: java.lang.Exception){
                e.printStackTrace()
                Toast.makeText(applicationContext, "Something went wrong, please logout and login again", Toast.LENGTH_SHORT).show()
            }
        }

//        val intent = Intent(applicationContext, PushNotificationService::class.java)
//        startService(intent)
    }

    private fun checkTtsEngine() {
        val installTtsIntent = Intent()
        installTtsIntent.action = TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA
        startActivity(installTtsIntent)
    }

}
