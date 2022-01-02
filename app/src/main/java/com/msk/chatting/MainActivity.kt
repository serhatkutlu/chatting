package com.msk.chatting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.msk.chatting.adapter.recyclerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import com.msk.chatting.model.Users as Users1

class MainActivity : AppCompatActivity() {

lateinit var userRecyclerView:RecyclerView
lateinit var userlist:ArrayList<Users1>
lateinit var adapter: recyclerAdapter
lateinit var mAuth:FirebaseAuth
lateinit var mDbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar =Toolbar
        setSupportActionBar(toolbar)


        mAuth=FirebaseAuth.getInstance()
        mDbRef= FirebaseDatabase.getInstance("https://chat-5ba3f-default-rtdb.europe-west1.firebasedatabase.app").getReference()

        userlist= arrayListOf()
        adapter=recyclerAdapter(userlist)
        Recycler_View.adapter=adapter
        Recycler_View.layoutManager=LinearLayoutManager(this)




        mDbRef.child("user").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userlist.clear()
                for ( postsnapshot in snapshot.children){

                    val currentuser=postsnapshot.getValue<com.msk.chatting.model.Users>()
                    if (mAuth.currentUser?.uid!=currentuser?.uid) {
                        userlist.add(currentuser!!)
                    }

                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuinflater=menuInflater
        menuinflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.logout){
            var intent=Intent(this,Login::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}