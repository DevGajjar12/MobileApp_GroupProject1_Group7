package com.example.mobileapp_groupproject1.Adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobileapp_groupproject1.R
import com.example.mobileapp_groupproject1.Entity.Recruit

class RecruitAdapter(private val context: Context, private val recruits: List<Recruit>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<RecruitAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(recruit: Recruit)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val skillsTextView: TextView = itemView.findViewById(R.id.skillsTextView)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(recruits[position])
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recruit_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recruit = recruits[position]
        holder.nameTextView.text = recruit.name
        holder.descriptionTextView.text = recruit.description
        holder.skillsTextView.text = recruit.skills

        Glide.with(context)
            .load(recruit.image)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return recruits.size
    }
}
