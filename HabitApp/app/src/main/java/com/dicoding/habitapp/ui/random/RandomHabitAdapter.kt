package com.dicoding.habitapp.ui.random

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.habitapp.R
import com.dicoding.habitapp.data.Habit

class RandomHabitAdapter(
    private val onClick: (Habit) -> Unit
) : RecyclerView.Adapter<RandomHabitAdapter.PagerViewHolder>() {

    private val habitMap = LinkedHashMap<PageType, Habit>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitData(key: PageType, habit: Habit) {
        habitMap[key] = habit
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PagerViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.pager_item, parent, false))

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val key = getIndexKey(position) ?: return
        val pageData = habitMap[key] ?: return
        holder.bind(key, pageData)
    }

    override fun getItemCount() = habitMap.size

    private fun getIndexKey(position: Int) = habitMap.keys.toTypedArray().getOrNull(position)

    enum class PageType {
        HIGH, MEDIUM, LOW
    }

    inner class PagerViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        //TODO 14 : Create view and bind data to item view
        private val tvtitlepager : TextView = itemView.findViewById(R.id.pager_tv_title)
        private val imagerpager : ImageView = itemView.findViewById(R.id.pager_priority)
        private val tvtimepager : TextView = itemView.findViewById(R.id.pager_tv_time)
        private val tvminutepager : TextView = itemView.findViewById(R.id.pager_tv_minutes)
        private val btncount : Button = itemView.findViewById(R.id.btn_count)


        fun bind(pageType: PageType, pageData: Habit) {
            tvtitlepager.text = pageData.title
            tvtimepager.text = pageData.startTime
            tvminutepager.text = pageData.minutesFocus.toString()

            when(pageType){
                PageType.HIGH->imagerpager.setImageResource(R.drawable.ic_priority_high)
                PageType.MEDIUM->imagerpager.setImageResource(R.drawable.ic_priority_medium)
                PageType.LOW->imagerpager.setImageResource(R.drawable.ic_priority_low)
            }
        btncount.setOnClickListener { onClick(pageData) }
        }
    }
}
