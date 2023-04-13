package com.example.bot
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class NotificationAdapter(private var mList: List<NotificationContent>) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>(){
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        // inflates the notification_item view, which is used for holding notification items in the list
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notification_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val notificationList = mList[position]


        // sets the text to the textview from our itemHolder class
        holder.textView_title.text = notificationList.title
        holder.textView_content.text = notificationList.content
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    fun updateNotifications(newNotifications: List<NotificationContent>) {
        mList = newNotifications
    }

    // Holds the views for adding it to text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView_title: TextView = itemView.findViewById(R.id.textView_title)
        val textView_content: TextView = itemView.findViewById(R.id.textView_content)
    }
}