package com.example.bot.fragments.dialog

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.bot.R

private const val TITLE = "param1"
private const val ADDRESS = "param2"
private const val DATE = "param3"
private const val TIME = "param4"

class EventDetailFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var title: String? = null
    private var address: String? = null
    private var date: String? = null
    private var time: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(TITLE)
            address = it.getString(ADDRESS)
            date = it.getString(DATE)
            time = it.getString(TIME)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_event_detail_fragment, container, false)
        getDialog()?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        // Inflate the layout for this fragment
        // Find TextViews
        val titleTextView = view.findViewById<TextView>(R.id.title_text_view)
        val addressTextView = view.findViewById<TextView>(R.id.address_text_view)
        val dateTextView = view.findViewById<TextView>(R.id.date_text_view)
        val timeTextView = view.findViewById<TextView>(R.id.time_text_view)

        // Set text to the TextViews
        titleTextView.text = title
        addressTextView.text = address
        dateTextView.text = date
        timeTextView.text = time


        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EventDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(eventData: com.example.bot.network.EventData) =
            EventDetailFragment().apply {
                arguments = Bundle().apply {

                    putString(TITLE, eventData.summary.toString())
                    putString(ADDRESS, eventData.location.toString())
                    val dateComponents = eventData.start.dateTime.split("T")
                    val dateString = dateComponents[0]
                    putString(DATE, dateString)
                    val timeString = eventData.start.dateTime.substring(11, 16)
                    putString(TIME, timeString)
                }
            }
    }
}