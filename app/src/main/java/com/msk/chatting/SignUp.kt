package com.msk.chatting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {
    private  var mAuth=FirebaseAuth.getInstance()
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var button_login: Button
    private lateinit var button_signup: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        name=Signup_name
        email=Signup_mail
        password=Signup_password
        button_login=signup_loginButton
        button_signup=signup_signupButton

        button_signup.setOnClickListener {
            var email=Signup_mail.text.toString()
            var password=Signup_password.text.toString()

            signup(email,password)
        }





    }
    fun signup(email:String,password:String){

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    var intent=Intent(this,MainActivity::class.java)
                    startActivity(intent)

                } else {
                    Toast.makeText(this@SignUp,"exeption",Toast.LENGTH_LONG).show()

                }
            }
    }
}