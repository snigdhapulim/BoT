package com.example.bot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.maps.model.DirectionsRoute
import okhttp3.Route

class MapRouteAdapter(private val routes: Array<DirectionsRoute>) : RecyclerView.Adapter<MapRouteAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.routes_list_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = routes[position]
        holder.nameTextView.text = "Route " + position
        holder.durationTextView.text = "Duration: ${ItemsViewModel.legs.sumOf { it.duration.inSeconds } / 60} mins"


        // sets the text to the textview from our itemHolder class
        //holder.textView.text = ItemsViewModel.text

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return routes.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var nameTextView: TextView = itemView.findViewById(R.id.text_view_route_name)
        var durationTextView: TextView = itemView.findViewById(R.id.text_view_route_duration)
    }
}