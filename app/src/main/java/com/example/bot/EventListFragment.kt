package com.example.bot

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bot.databinding.FragmentEventListBinding
import com.example.bot.network.EmailRequestBody
import com.example.bot.network.UserAPI
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.launch

private const val TAG = "EventListFragment"

class EventListFragment: Fragment() {

    private var _binding: FragmentEventListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val eventListViewModel: EventListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val acco = GoogleSignIn.getLastSignedInAccount(requireContext())
        val email = acco?.email.toString()
        Log.d(TAG, "Email: ${email}")
        eventListViewModel.viewModelScope.launch {
            val requestBody = EmailRequestBody(email)
            val events = UserAPI.FetchCalendarEventsAPI.retrofitFetchCalendarEventsService.fetchCalendarEvents(requestBody)
            eventListViewModel.eventList.clear()
            eventListViewModel.eventList.addAll(events)
            eventListViewModel.setOnEventListChangedListener(object : EventListViewModel.OnEventListChangedListener {
                override fun onEventListChanged(events: List<com.example.bot.network.EventData>) {
                    val adapter = EventListAdapter(events)
                    binding.eventRecyclerView.adapter = adapter
                }
            })
            Log.d(TAG, "Total events: ${eventListViewModel.eventList.size}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventListBinding.inflate(inflater, container, false)
        binding.eventRecyclerView.layoutManager = LinearLayoutManager(context)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}