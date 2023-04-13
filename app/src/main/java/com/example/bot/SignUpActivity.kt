package com.example.bot

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class SignUpActivity : AppCompatActivity() {

    lateinit var google : ImageView
    lateinit var notify : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        var mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        google = findViewById(R.id.google)

        notify = findViewById(R.id.notify)

        google.setOnClickListener{
                val signInIntent = mGoogleSignInClient.signInIntent
                startActivityForResult(signInIntent, 1316)

        }

        notify.setOnClickListener{
            Toast.makeText(this, "You 've turned notify on ", Toast.LENGTH_SHORT).show()
            val intentNotification = Intent(this, NotificationActivity::class.java)
            startActivity(intentNotification)
        }

    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        val acco = GoogleSignIn.getLastSignedInAccount(this)
//        if (acco!=null){
//    Toast.makeText(this, acco.displayName, Toast.LENGTH_SHORT).show()
//
//        }else{
//            Toast.makeText(this, "None receive", Toast.LENGTH_SHORT).show()
//
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1316) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                task.getResult(ApiException::class.java)


//                val acco = GoogleSignIn.getLastSignedInAccount(this)
//                    if (acco!=null){
//                Toast.makeText(this, acco.displayName, Toast.LENGTH_SHORT).show()
////                        Toast.makeText(this, acco.displayName, Toast.LENGTH_SHORT).show()
//
//                    }
                Log.d("Signup",data.toString())
                println("result "+data.toString())
//                navigateToSecondActivity()


            } catch (e: ApiException) {
                Log.d("Signup","oh no")
                e.printStackTrace()
                e.message
                e.message?.let { Log.d("Signup", it) }

                print(e.message)
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}