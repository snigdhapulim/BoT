package com.example.bot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.maps.model.DirectionsRoute
import okhttp3.Route

class MapRouteAdapter(private val routes: Array<DirectionsRoute>) : RecyclerView.Adapter<MapRouteAdapter.ViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.routes_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = routes[position]
        holder.nameTextView.text = "Route " + (position+1)
        holder.durationTextView.text = "Distance: ${item.legs.sumOf { it.distance.inMeters } / 1000.0} km \nDuration: ${item.legs.sumOf { it.duration.inSeconds } / 60} mins"

        holder.radioButton.isChecked = position == selectedPosition

        holder.itemView.setOnClickListener {
            selectedPosition = holder.adapterPosition
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return routes.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameTextView: TextView = itemView.findViewById(R.id.text_view_route_name)
        var durationTextView: TextView = itemView.findViewById(R.id.text_view_route_duration)
        var radioButton: RadioButton = itemView.findViewById(R.id.radio_button_route)
    }
}
