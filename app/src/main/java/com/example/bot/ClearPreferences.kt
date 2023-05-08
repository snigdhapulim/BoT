package com.example.bot

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class ClearPreferences : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        LoginOut(context)
    }

    fun LoginOut(context: Context){

        val acco = GoogleSignIn.getLastSignedInAccount(context)
        //when acco is not null means the user has login before
        if (acco!=null){
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
            var mGoogleSignInClient = GoogleSignIn.getClient(context, gso)
            mGoogleSignInClient.signOut().addOnCompleteListener {
                val profilePhotoDrawable = ContextCompat.getDrawable(context, R.drawable.profile)
                val activity = context as Activity
                val intent = Intent(activity, SignUpActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                activity?.finishAffinity()
                context.startActivity(intent)
            }
        }
    }
}