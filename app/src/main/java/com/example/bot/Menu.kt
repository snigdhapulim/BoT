package com.example.bot

//import RetrieveCalendarEventsTask
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class Menu() : DialogFragment() {

    private lateinit var profile_button: LinearLayout
    private lateinit var events_button: LinearLayout
    private lateinit var logout_button: LinearLayout
    private lateinit var add_event_button: LinearLayout
    private lateinit var menuUsername : TextView
    private lateinit var logoutext : TextView


    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = (ViewGroup.LayoutParams.WRAP_CONTENT)
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog?.getWindow()?.setBackgroundDrawable(null)
            dialog.getWindow()?.setLayout(width, height)
            dialog?.getWindow()?.setGravity(Gravity.START)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        println("Menu resume")
        val view = requireView()
        menuUsername = view.findViewById(R.id.menu_username)
        val acco = GoogleSignIn.getLastSignedInAccount(requireContext())

        if (acco!=null){
                logoutext.setText(R.string.logout)
                val token = acco?.idToken
                Log.i("token is", acco.idToken.toString())
//                val task = RetrieveCalendarEventsTask(token)
//                task.execute()

            val profilePhoto = view.findViewById<ImageView>(R.id.imageView)
//            constraintLayout.setBackgroundResource(acco.photoUrl)
//            val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, acco.photoUrl)
//            val bitmapDrawable = BitmapDrawable(resources, bitmap)
//            constraintLayout.background = bitmapDrawable
            menuUsername.setText(acco.displayName)

            Glide.with(this).load(acco.photoUrl).into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
//                    profilePhoto.background = resource
                    profilePhoto.setImageDrawable(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // not implemented
                }
            })




        }



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        profile_button = view.findViewById(R.id.profile_button)
        events_button = view.findViewById(R.id.events_button)
        logout_button = view.findViewById(R.id.logout_button)
        add_event_button = view.findViewById(R.id.add_event_button)

        val currentActivity = requireActivity()
        var compactActivity = AppCompatActivity()

        if (currentActivity is MainActivity) {
            // Call the activity method
            add_event_button.setBackground(resources.getDrawable(R.drawable.active_menu_background))
            compactActivity = (activity as MainActivity)
        } else if(currentActivity is EventsViewActivity) {
            events_button.setBackground(resources.getDrawable(R.drawable.active_menu_background))
            compactActivity = (activity as EventsViewActivity)
        } else if(currentActivity is AddEvent) {
            compactActivity = (activity as AddEvent)
        } else if(currentActivity is EditEvent) {
            compactActivity = (activity as EditEvent)
        }


        profile_button.setOnClickListener{
            var profile:Profile= Profile()
            val fragmentManager = compactActivity.supportFragmentManager
            profile.show(fragmentManager, "hello")
        }

        events_button.setOnClickListener{
            val intent = Intent(compactActivity, EventsViewActivity::class.java)
            compactActivity.startActivity(intent)
        }

        add_event_button.setOnClickListener{
            val intent = Intent(compactActivity, MainActivity::class.java)
            compactActivity.startActivity(intent)
        }

        logoutext = view.findViewById(R.id.logout)




        logout_button.setOnClickListener {
            LoginOut(requireContext())
        }
//        println("Menu started")




        return view
    }


    //google account log in or log out
    fun LoginOut(context: Context){

        val acco = GoogleSignIn.getLastSignedInAccount(requireContext())
        //when acco is not null means the user has login before
        if (acco!=null){
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build()
                var mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
                mGoogleSignInClient.signOut().addOnCompleteListener {

                    menuUsername.setText("NaN")

                    Toast.makeText(requireContext(), "Successfully logout ", Toast.LENGTH_SHORT)
                        .show()
                    logoutext.setText(R.string.login)

                    // get "imageView" in fragment_menu.xml, replace user photo with original "nan" photo.
                    val profilePhotoImageView = requireView().findViewById<ImageView>(R.id.imageView)
                    val profilePhotoDrawable = ContextCompat.getDrawable(context, R.drawable.profile)

                    profilePhotoImageView.setImageDrawable(profilePhotoDrawable)


            }

        }else {
                Toast.makeText(requireContext(), "Go to login", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(requireContext(), SignUpActivity::class.java)
                requireActivity().startActivity(intent)

        }

    }
}