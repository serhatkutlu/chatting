package com.msk.chatting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.msk.chatting.adapter.MessageAdapter
import kotlinx.android.synthetic.main.activity_chat.*

class chat : AppCompatActivity() {


    private lateinit var messagebox:EditText
    private lateinit var sendbuttlon:ImageView
    private lateinit var messagelist:ArrayList<com.msk.chatting.model.Message>
    private lateinit var messageAdapter:MessageAdapter
    private lateinit var mDbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {

        var receiverRoom:String?=null
        var senderRoom:String?=null

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)


        var name=intent.getStringExtra("name")
        var receiveruid=intent.getStringExtra("uid")

        val senderuid=FirebaseAuth.getInstance().currentUser!!.uid
        mDbRef=FirebaseDatabase.getInstance("https://chat-5ba3f-default-rtdb.europe-west1.firebasedatabase.app").getReference()

        senderRoom=receiveruid+senderuid
        receiverRoom=senderuid+receiveruid
        setSupportActionBar(chatToolbar)
        supportActionBar?.let {
            it.title=name
        }




        messagebox=Messagebox
        sendbuttlon=SendButton
        messagelist=ArrayList()
        messageAdapter=MessageAdapter(messagelist)

        ChatRecycler.adapter=messageAdapter
        ChatRecycler.layoutManager=LinearLayoutManager(this)

        mDbRef.child("chats").child(senderRoom!!).child("messages").addValueEventListener(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messagelist.clear()
                for (snap in snapshot.children){
                    val message=snap.getValue<com.msk.chatting.model.Message>()
                    println(message!!.message)
                    messagelist.add(message!!)
                }
                messageAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        sendbuttlon.setOnClickListener{
            val message=messagebox.text.toString()

            val message_obj=com.msk.chatting.model.Message(message,senderuid)
            mDbRef.child("chats").child(senderRoom).child("messages").push().setValue(message_obj).addOnSuccessListener {
                mDbRef.child("chats").child(receiverRoom).child("messages").push().setValue(message_obj)

            }
            messagebox.setText("")

        }


    }
}