package com.msk.chatting.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.msk.chatting.R
import com.msk.chatting.model.Message
import kotlinx.android.synthetic.main.receive.view.*
import kotlinx.android.synthetic.main.send.view.*

class MessageAdapter(val messagelist:ArrayList<Message>):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    val Item_Receve=1
    val Item_Sent=2

    class SendViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val sendMessage=itemView.txt_sentmassage
    }
    class ReceiveViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val receiveMessage=itemView.txt_receivemassage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType==1){
            val view=LayoutInflater.from(parent.context).inflate(R.layout.receive,parent,false)
             return ReceiveViewHolder(view)

        }else{
            val view=LayoutInflater.from(parent.context).inflate(R.layout.send,parent,false)
            return SendViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage=messagelist.get(position)
        if (holder.javaClass==SendViewHolder::class.java){
            val viewHolder=holder as SendViewHolder
            holder.sendMessage.text=currentMessage.message

        }else{
            val viewHolder=holder as ReceiveViewHolder
            holder.receiveMessage.text=currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage=messagelist.get(position)
        if (FirebaseAuth.getInstance().currentUser?.uid.equals((currentMessage.senderId))){
            return Item_Sent
        }
        else{
            return Item_Receve
        }
    }
    override fun getItemCount(): Int {
        return  messagelist.size
    }
}