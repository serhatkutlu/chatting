package com.msk.chatting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    private lateinit  var  mAuth:FirebaseAuth
    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var button_login:Button
    private lateinit var button_signup:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        email=login_mail
        password=login_password
        button_login=login_loginButton
        button_signup=login_signupButton

        mAuth=FirebaseAuth.getInstance()


        button_signup.setOnClickListener{
            var intent=Intent(this,SignUp::class.java)
            startActivity(intent)
        }
        button_login.setOnClickListener {
           var email=email.text.toString()
            var password=password.text.toString()
            login(email,password)
        }
    }







    fun login(email:String,password:String ){
    mAuth.signInWithEmailAndPassword(email,password)
    .addOnCompleteListener{
        if (it.isSuccessful){
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        else{
            Toast.makeText(this,"exeption",Toast.LENGTH_LONG).show()
        }


}
    }
}