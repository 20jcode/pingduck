package com.sosal.pingduck.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sosal.pingduck.R
import com.sosal.pingduck.msgDB.MsgDTO

class MsgAdapter(val msgList: List<MsgDTO>, val onClick: (MsgDTO)->(Unit)) : RecyclerView.Adapter<MsgAdapter.MsgViewHolder>() {

    //클릭 이벤트 처리
    private lateinit var itemClickListener : OnItemClickListener

    inner class MsgViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textMsgTarget: TextView = itemView.findViewById(R.id.textMsgTarget)
        val textMsgPinkTime: TextView = itemView.findViewById(R.id.textMsgPinkTime)
        val textMsgCreateTime: TextView = itemView.findViewById(R.id.textMsgCreateTime)
        val textMsgPinkWhy: TextView = itemView.findViewById(R.id.textMsgPinkWhy)
        val textGeneratedMsg: TextView = itemView.findViewById(R.id.textGeneratedMsg)

        fun bind(msg : MsgDTO){
            itemView.setOnClickListener{
                onClick(msg)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MsgViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_msg, parent, false)
        return MsgViewHolder(view)
    }

    override fun onBindViewHolder(holder: MsgViewHolder, position: Int) {
        val msg = msgList[position]
        holder.textMsgTarget.text = "Target: ${msg.getMsgTarget()}"
        holder.textMsgPinkTime.text = "Pink time: ${msg.getMsgPinkTime()}"
        holder.textMsgCreateTime.text = "Create Time: ${msg.getMsgCreateTime()}"
        holder.textMsgPinkWhy.text = "Pink Why: ${msg.getMsgPinkWhy()}"
        holder.textGeneratedMsg.text = "Generated Msg: ${msg.getGeneratedMsg()}"
        holder.bind(msg)
    }

    override fun getItemCount(): Int {
        return msgList.size
    }
    
}
