package com.example.bot

//import RetrieveCalendarEventsTask
import android.annotation.SuppressLint
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
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject


class SignUpActivity : AppCompatActivity() {

    lateinit var google : ImageView
    lateinit var notify : ImageView
    lateinit var token: String


    override fun onCreate(savedInstanceState: Bundle?) {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestServerAuthCode(getString(R.string.default_web_client_id))
            .requestScopes(Scope("https://www.googleapis.com/auth/calendar"), Scope("https://www.googleapis.com/auth/calendar.events"), Scope("https://www.googleapis.com/auth/contacts.readonly"), Scope("https://www.googleapis.com/auth/userinfo.profile"), Scope("https://www.googleapis.com/auth/userinfo.email"), Scope("https://www.googleapis.com/auth/plus.login"), Scope("https://www.googleapis.com/auth/drive.appdata"), Scope("https://www.googleapis.com/auth/gmail.readonly"), Scope("https://www.googleapis.com/auth/gmail.compose"), Scope("https://www.googleapis.com/auth/gmail.modify"), Scope("https://www.googleapis.com/auth/gmail.labels"), Scope("https://www.googleapis.com/auth/gmail.send"), Scope("https://www.googleapis.com/auth/tasks"))
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
                val account = task.getResult(ApiException::class.java)
//                val token = account?.idToken
                Log.i("token is", account?.serverAuthCode.toString())

                val acco = GoogleSignIn.getLastSignedInAccount(this)
                if (acco!=null){
                Toast.makeText(this,"Welcome, " + acco.displayName, Toast.LENGTH_SHORT).show()
//
                    }
//                Toast.makeText(applicationContext, data.toString(), Toast.LENGTH_SHORT)
//                    .show()
                Log.d("Signup",data.toString())
                println("result "+data.toString())
//                navigateToSecondActivity()

                finish()

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