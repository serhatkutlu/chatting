package com.msk.chatting.adapter

import android.content.Context
import android.service.autofill.UserData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.msk.chatting.R
import com.msk.chatting.model.Users
import kotlinx.android.synthetic.main.userlayout.view.*

class recyclerAdapter( var context: Context,var list: List<Users>):
    RecyclerView.Adapter<recyclerAdapter.UserViewHolder>() {
    class UserViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        //val name=itemView.user_name

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view:View=LayoutInflater.from(context).inflate(R.layout.userlayout,parent,false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.itemView.user_name.text=list[position].name
        println("recycler")
    }

    override fun getItemCount(): Int {
        return list.size
    }
}