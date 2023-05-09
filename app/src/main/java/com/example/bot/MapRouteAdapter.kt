package com.example.bot

import android.animation.ValueAnimator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.maps.model.DirectionsRoute
import com.google.maps.model.DirectionsStep
import com.google.maps.model.TravelMode
import okhttp3.Route
import org.w3c.dom.Text

class MapRouteAdapter(private val routes: Array<DirectionsRoute>) : RecyclerView.Adapter<MapRouteAdapter.ViewHolder>() {

    private var selectedPosition = 0
    private var first=true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.routes_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = routes[position]
        holder.nameTextView.text = "Route " + (position+1)
        holder.durationTextView.text = "Distance: ${item.legs.sumOf { it.distance.inMeters } / 1000.0} km \nDuration: ${item.legs.sumOf { it.duration.inSeconds } / 60} mins"

        val steps = routes[position].legs.flatMap { it.steps.toList() }
        var textRoute:String=""
        for (j in steps.indices) {
            val step = steps[j]
            if (step.travelMode == TravelMode.TRANSIT) {
                val transitDetails = step.transitDetails
                textRoute=textRoute+"${j + 1}. Take the ${transitDetails.line.shortName} ${transitDetails.headsign}\n"
                textRoute=textRoute+"    Depart from ${transitDetails.departureStop.name} at ${transitDetails.departureTime}\n"
                textRoute=textRoute+"    Arrive at ${transitDetails.arrivalStop.name} at ${transitDetails.arrivalTime}\n"
            } else {
                textRoute=textRoute+"${j + 1}. ${step.htmlInstructions}\n"
            }
        }
        holder.routeTextView.text=textRoute

        if (selectedPosition==position){
            holder.listElement.background= ContextCompat.getDrawable(holder.itemView.context, R.drawable.routes_list_selected)

            if(first ==false) {
                val valueAnimator =
                    ValueAnimator.ofInt(
                        360, 360 +
                                holder.routeTextView.layout.height
                    )
                valueAnimator.duration = 350L
                valueAnimator.addUpdateListener {
                    val animatedValue = valueAnimator.animatedValue as Int
                    val layoutParams = holder.listElement.layoutParams
                    layoutParams.height = animatedValue
                    holder.listElement.layoutParams = layoutParams
                }
                valueAnimator.start()
            }
            else{
                holder.listElement.layoutParams.height=ViewGroup.LayoutParams.WRAP_CONTENT
            }

        }
        else{
            holder.listElement.background=ContextCompat.getDrawable(holder.itemView.context, R.drawable.routes_list)
            if(holder.listElement.layoutParams.height!=360) {
                val valueAnimator =
                    ValueAnimator.ofInt(holder.listElement.measuredHeight, 360 )
                valueAnimator.duration = 350L
                valueAnimator.addUpdateListener {
                    val animatedValue = valueAnimator.animatedValue as Int
                    val layoutParams = holder.listElement.layoutParams
                    layoutParams.height = animatedValue
                    holder.listElement.layoutParams = layoutParams
                }
                valueAnimator.start()
            }
        }
        holder.itemView.setOnClickListener {
            selectedPosition = position
            if(first){
                first=false
            }
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return routes.size
    }

    // Public method to get the current selected position
    fun getSelectedPosition(): Triple<Double, Long, List<DirectionsStep>> {
        var distance=routes[selectedPosition].legs.sumOf { it.distance.inMeters } / 1000.0
        var duration=routes[selectedPosition].legs.sumOf { it.duration.inSeconds } / 60
        var steps=routes[selectedPosition].legs.flatMap { it.steps.toList() }
        return Triple(distance,duration,steps)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameTextView: TextView = itemView.findViewById(R.id.text_view_route_name)
        var durationTextView: TextView = itemView.findViewById(R.id.text_view_route_duration)
        var routeTextView:TextView=itemView.findViewById(R.id.text_view_route)
        var listElement: LinearLayout=itemView.findViewById(R.id.list)
    }
}
