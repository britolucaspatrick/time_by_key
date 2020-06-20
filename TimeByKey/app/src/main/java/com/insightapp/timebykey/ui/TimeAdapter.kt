package com.insightapp.timebykey.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.insightapp.timebykey.R
import com.insightapp.timebykey.entity.TimeByKey
import kotlinx.android.synthetic.main.item.view.*

class TimeAdapter internal constructor(
    context: Context,
    itemSelectedListener: ItemSelectedListener
) : RecyclerView.Adapter<TimeAdapter.TimeViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var times = emptyList<TimeByKey>()
    private var itemSelectedListener: ItemSelectedListener
    private val context: Context

    init {
        this.context = context
        this.itemSelectedListener = itemSelectedListener
    }

    interface ItemSelectedListener {
        fun onItemSelectedDel(item: TimeByKey)
        fun onItemSelectedDateEnd(item: TimeByKey)
        fun onItemSelectedDateStart(item: TimeByKey)
    }

    inner class TimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val key  = itemView.key_time
        val desc_date_start = itemView.date_start
        val desc_date_end = itemView.date_end
        val btn_date_end = itemView.btn_date_end
        val btn_date_del = itemView.btn_date_del
        val btn_date_start = itemView.btn_date_start
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val itemView = inflater.inflate(R.layout.item, parent, false)
        return TimeViewHolder(itemView)
    }

    override fun getItemCount() = times.size

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        val current = times[position]
        holder.key.text = current.key_time
        holder.desc_date_start.text = current.inicio?.toString()
        holder.desc_date_end.text = current.fim?.toString()

        holder.btn_date_del.setOnClickListener {
            itemSelectedListener.onItemSelectedDel(current)
        }

        holder.btn_date_end.setOnClickListener {
            itemSelectedListener.onItemSelectedDateEnd(current)
        }

        holder.btn_date_start.setOnClickListener {
            itemSelectedListener.onItemSelectedDateStart(current)
        }
    }

    internal fun setTimeByKey(time: List<TimeByKey>) {
        this.times = time
        notifyDataSetChanged()
    }

}