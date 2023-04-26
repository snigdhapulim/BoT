package com.example.bot

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.bot.network.UserAPI
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class Profile : DialogFragment(){
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var name : TextView
    private lateinit var email : TextView
    private lateinit var image : ImageView
    private lateinit var cautionText : TextView
    private lateinit var addressLayout : LinearLayout
    private lateinit var autoComplete : View
    private lateinit var addressText: TextView
    private lateinit var zipCodeText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
//
//    public fun set(z: String){
//        param1 = z;
//
//    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        getDialog()?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        val acco = GoogleSignIn.getLastSignedInAccount(requireContext())
        var view = inflater.inflate(R.layout.fragment_profile, container, false)
        autoComplete = view.findViewById(R.id.autocomplete_profile_fragment)
        addressLayout = view.findViewById(R.id.address_layout)
        if (acco!=null) {
            name = view.findViewById(R.id.profileName)
            email = view.findViewById(R.id.profileEmail)
            image = view.findViewById(R.id.profileImageView)
            cautionText = view.findViewById(R.id.caution_text)
            addressText = view.findViewById(R.id.profileAddress)
            zipCodeText = view.findViewById(R.id.profilePin)
            name.setText(acco.displayName.toString())
            email.setText(acco.email.toString())

            Glide.with(this).load(acco.photoUrl).into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    image.layoutParams.width=(114*resources.displayMetrics.density).toInt()
                    image.layoutParams.height=(114*resources.displayMetrics.density).toInt()
                    image.setImageDrawable(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // not implemented

                }
            })

            CoroutineScope(Dispatchers.Main).launch {
                var homeCheck = UserAPI.CheckHomeAPI.retrofitCheckHomeService.checkHome(acco.email.toString())
                if(homeCheck.success.toString().toBoolean()){
                    cautionText.visibility = View.GONE
                    autoComplete.visibility = View.GONE
                    val addressWithoutZip = homeCheck.address?.substringBeforeLast(" ")
                    val addressBeforeZip = addressWithoutZip?.substringBeforeLast(" ")
                    val pinCode = homeCheck.address!!.split(",")[2].trim().split(" ").last()
                    addressText.setText(addressBeforeZip)
                    zipCodeText.setText(pinCode)

                }else{
                    cautionText.visibility = View.VISIBLE
                    autoComplete.visibility = View.VISIBLE
                    addressLayout.visibility = View.GONE
                }
            }

            if (!Places.isInitialized()) {
                Places.initialize(requireContext(), "AIzaSyAmjj4km9mc04VEvtj3mqVEYH6L7kc2vks")
            }

            // Initialize the AutocompleteSupportFragment.
            val autocompleteFragment =
                childFragmentManager.findFragmentById(R.id.autocomplete_profile_fragment)
                        as AutocompleteSupportFragment

            // Specify the types of place data to return.
            autocompleteFragment.setPlaceFields(listOf(Place.Field.ADDRESS))

            // Set up a PlaceSelectionListener to handle the response.
            autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
                override fun onPlaceSelected(place: Place) {
                    // TODO: Get info about the selected place.
                    Log.i("Add Event", "Place:${place.address}")
                    CoroutineScope(Dispatchers.Main).launch {
                        var addr = UserAPI.HomeAddress(acco.email.toString(), place.address.toString())
                        UserAPI.UpdateAddressAPI.retrofitUpdateAddressService.updateAddress(addr)
                    }
                    // get the FragmentManager instance
                    val fragmentManager = requireActivity().supportFragmentManager

                    // clear the back stack by popping all fragments
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

                }

                override fun onError(status: Status) {
                    // TODO: Handle the error.
                    Log.i("Add Event", "An error occurred: $status")
                }
            })
        }

        return view
    }
}