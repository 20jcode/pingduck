package com.sosal.pingduck

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sosal.pingduck.msgDB.MsgDTO

class MsgAdapter(val msgList: List<MsgDTO>) : RecyclerView.Adapter<MsgAdapter.MsgViewHolder>() {
    inner class MsgViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textMsgTarget: TextView = itemView.findViewById(R.id.textMsgTarget)
        val textMsgPinkTime: TextView = itemView.findViewById(R.id.textMsgPinkTime)
        val textMsgCreateTime: TextView = itemView.findViewById(R.id.textMsgCreateTime)
        val textMsgPinkWhy: TextView = itemView.findViewById(R.id.textMsgPinkWhy)
        val textGeneratedMsg: TextView = itemView.findViewById(R.id.textGeneratedMsg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MsgViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_msg, parent, false)
        return MsgViewHolder(view)
    }

    override fun onBindViewHolder(holder: MsgViewHolder, position: Int) {
        val msg = msgList[position]
        holder.textMsgTarget.text = "Target: " + msg.getMsgTarget()
        holder.textMsgPinkTime.text = "Pink time: " + msg.getMsgPinkTime()
        holder.textMsgCreateTime.text = "Create Time: " + msg.getMsgCreateTime()
        holder.textMsgPinkWhy.text = "Pink Why: " + msg.getMsgPinkWhy()
        holder.textGeneratedMsg.text = "Generated Msg: " + msg.getGeneratedMsg()
    }

    override fun getItemCount(): Int {
        return msgList.size
    }
}
