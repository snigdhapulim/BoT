package com.example.bot
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class NotificationAdapter(private var nList: List<NotificationContent>) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>(){
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        // inflates the notification_item view, which is used for holding notification items in the list
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notification_item, parent, false)

        return ViewHolder(view)
    }

    // binds ViewHolders to data from the model
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notification = nList[position]
        // bind view with data
        holder.bind(notification)

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return nList.size
    }


    // Define a view holder to hold references to each item view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(notification: NotificationContent) {
            val textView_title: TextView = itemView.findViewById(R.id.textView_title)
            val textView_content: TextView = itemView.findViewById(R.id.textView_content)
            val textView_updateTime: TextView = itemView.findViewById(R.id.textView_updateTime)
            textView_title.text = notification.title
            textView_content.text = notification.description
            textView_updateTime.text = notification.updateTime
        }
    }
}