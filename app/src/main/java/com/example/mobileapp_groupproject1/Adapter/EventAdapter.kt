package com.example.mobileapp_groupproject1.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobileapp_groupproject1.Entity.Event
import com.example.mobileapp_groupproject1.Entity.Recruit
import com.example.mobileapp_groupproject1.R

class EventAdapter(
    private val context: Context,
    private val event: List<Event>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(event: Event)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val iv_iamge: ImageView = itemView.findViewById(R.id.iv_iamge)
        val tv_title: TextView = itemView.findViewById(R.id.tv_title)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(event[position])
            }
        }
    }

    override fun onBindViewHolder(holder: EventAdapter.ViewHolder, position: Int) {
        val event = event[position]
        holder.tv_title.text = event.name

        Glide.with(context)
            .load(event.image)
            .into(holder.iv_iamge)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return event.size
    }
}