package com.insightapp.timebykey

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.insightapp.timebykey.entity.TimeByKey
import kotlinx.android.synthetic.main.item.view.*

class TimeAdapter internal constructor(
    context: Context,
    itemSelectedListener:ItemSelectedListener
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
        fun onItemSelectedAlt(item: TimeByKey)
    }

    inner class TimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val descr  = itemView.descricao
        val btn_edit = itemView.btn_edit
        val btn_del = itemView.btn_del
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val itemView = inflater.inflate(R.layout.item, parent, false)
        return TimeViewHolder(itemView)
    }

    override fun getItemCount() = times.size

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        val current = times[position]
        holder.descr.text = current.key + " " + current.inicio + " " + current.fim


        holder.btn_del.setOnClickListener {
            itemSelectedListener.onItemSelectedDel(current)
        }

        holder.btn_edit.setOnClickListener {
            itemSelectedListener.onItemSelectedAlt(current)
        }
    }

    internal fun setTimeByKey(time: List<TimeByKey>) {
        this.times = time
        notifyDataSetChanged()
    }

}