package com.msk.chatting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.msk.chatting.model.Users
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {
    private lateinit  var mAuth:FirebaseAuth
    private lateinit var mDBRef:DatabaseReference
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText

    private lateinit var button_signup: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        name=Signup_name
        email=Signup_mail
        password=Signup_password
        button_signup=signup_signupButton
        mAuth=FirebaseAuth.getInstance()

        button_signup.setOnClickListener {
            var _name=name.text.toString()
            var _email=email.text.toString()
            var _password=password.text.toString()

            signup(_name,_email,_password)
            //AddToDatabaseUser("_name","_email","5555")
        }





    }
    fun signup(name:String,email:String,password:String){

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    AddToDatabaseUser(name,email,mAuth.currentUser?.uid!!)

                    var intent=Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    Toast.makeText(this@SignUp,"exeption",Toast.LENGTH_LONG).show()

                }
            }
    }
    fun AddToDatabaseUser(name:String,email:String,uid:String){


        mDBRef= FirebaseDatabase.getInstance("https://chat-5ba3f-default-rtdb.europe-west1.firebasedatabase.app").getReference()
        mDBRef.child("user").child(uid).setValue(Users(name,email,uid))


    }
}