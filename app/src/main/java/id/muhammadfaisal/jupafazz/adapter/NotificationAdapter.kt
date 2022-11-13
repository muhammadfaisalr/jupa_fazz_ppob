package id.muhammadfaisal.jupafazz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.api.model.notification.NotificationResponse
import id.muhammadfaisal.jupafazz.databinding.ItemNotificationBinding
import id.muhammadfaisal.jupafazz.helper.ViewHelper
import id.muhammadfaisal.jupafazz.utils.Font
import org.apache.commons.lang3.StringUtils

class NotificationAdapter(val context: Context, val notifications: ArrayList<NotificationResponse>) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.context, this.notifications[position])
    }

    override fun getItemCount(): Int {
        return notifications.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = ItemNotificationBinding.bind(this.itemView)

        fun bind(context: Context, notificationResponse: NotificationResponse) {
            this.binding.apply {
                this.textTitle.text = notificationResponse.title
                this.textDate.text = notificationResponse.createDate

                if (StringUtils.isNotEmpty(notificationResponse.image)) {
                    Glide
                        .with(context)
                        .load(notificationResponse.image)
                        .error(com.denzcoskun.imageslider.R.drawable.error)
                        .into(this.imageView)
                } else {
                    ViewHelper.gone(this.cardView)
                }

                Font.setInto((context as AppCompatActivity), Font.Rubik.MEDIUM, this.textTitle)
                Font.setInto((context as AppCompatActivity), Font.Rubik.REGULAR, this.textDate)
            }
        }
    }
}