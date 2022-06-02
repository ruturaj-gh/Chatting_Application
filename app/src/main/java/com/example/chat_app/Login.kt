package com.example.chat_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.chat_app.databinding.ActivityLoginBinding
import com.example.chat_app.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.handleCoroutineException
import org.json.JSONException
import java.lang.NullPointerException

class Login : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding

    private  lateinit var EditEmail:EditText
    private  lateinit var editpass:EditText
    private  lateinit var btnLogin :Button
    private  lateinit var btnsignup:Button

    private lateinit var mAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        mAuth=FirebaseAuth.getInstance()


        binding.buttonSignUp.setOnClickListener{
            val intent=Intent(this,SignUp::class.java)
            startActivity(intent)
        }

        binding.buttonLogin.setOnClickListener{

            val email=binding.editEmail.text.toString()
            val password=binding.editPassword.text.toString()
            login(email,password);
        }

    }
    private fun login(email:String,password:String){

        //logic for logging in the user
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent=Intent(this@Login,MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this@Login,"User does not exist",Toast.LENGTH_SHORT).show()
                }
            }
    }
}