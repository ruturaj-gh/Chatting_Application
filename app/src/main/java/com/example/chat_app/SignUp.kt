package com.example.chat_app

import android.content.Intent
import com.example.chat_app.Login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.chat_app.databinding.ActivityLoginBinding
import com.example.chat_app.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var editName:EditText
    private  lateinit var EditEmail: EditText
    private  lateinit var editpass: EditText
    private  lateinit var btnsignup: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding=ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth=FirebaseAuth.getInstance()

        binding.buttonSignUp.setOnClickListener{
            val name=binding.editName.text.toString()
            val email=binding.editEmail.text.toString()
            val password=binding.editPassword.text.toString()
            signUp(name,email,password)
        }

    }
    private fun signUp(name:String,email:String,password:String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                    val intent=Intent(this@SignUp,MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this@SignUp,task.exception.toString(),Toast.LENGTH_SHORT).show()


                }
            }


    }
    private fun addUserToDatabase(name:String,email:String,uid:String){

        mDbRef=FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(user(name,email,uid))

    }
}