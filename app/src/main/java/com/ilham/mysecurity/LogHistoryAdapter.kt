package com.ilham.mysecurity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LogHistoryAdapter (private val userList:ArrayList<User>): RecyclerView.Adapter<LogHistoryAdapter.LogHistoryViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogHistoryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_detect_history,parent,false)
        return LogHistoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LogHistoryViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.logName.text = currentItem.name
        holder.logTime.text = currentItem.time.toString()
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    class LogHistoryViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val logName : TextView = itemView.findViewById(R.id.tv_log_name)
        val logTime : TextView = itemView.findViewById(R.id.tv_log_time)

    }
}