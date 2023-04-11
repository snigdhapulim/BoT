package com.example.bot

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
/**
 * A simple [Fragment] subclass.
 * Use the [Event.newInstance] factory method to
 * create an instance of this fragment.
 */
//class Event : Fragment() {
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_event, container, false)
//    }
//}
class Event(private val event: Event) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_event, container, false)
//        view.findViewById<TextView>(R.id.titleTextView).text = event.title
//        view.findViewById<TextView>(R.id.dateTextView).text = event.date
        return view
    }

    companion object {
        fun newInstance(event: Event) = Event(event)
    }
}
