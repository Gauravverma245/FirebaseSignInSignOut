package com.example.learningfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         auth = FirebaseAuth.getInstance()

        SignUpButton.setOnClickListener {
            registerUser()
        }

        SignInButton.setOnClickListener {
            logIn()
        }

        SignOutButton.setOnClickListener {
            justOut()
        }
    }

    private fun justOut(){
        auth.signOut()
        checkLoginState()
    }

    override fun onStart() {
        super.onStart()
        checkLoginState()
    }

    private fun registerUser(){
        val email = SignUpEmail.text.toString()
        val password = SignUpPassword.text.toString()
        if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Email/Password can't be empty", Toast.LENGTH_SHORT).show()
        }
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
            task->
            if(task.isSuccessful){
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                checkLoginState()
            }
            else{
                Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun checkLoginState(){
        if(auth.currentUser == null){
            ChangeText.text = "You are not logged In!"
        }
        else{
            ChangeText.text = "You are logged In!"
        }
    }

    private fun logIn(){
        val email = SignInEmail.text.toString()
        val password = SignInPassword.text.toString()
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Email/Password can't be empty", Toast.LENGTH_SHORT).show()
        }
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                task->
            if(task.isSuccessful){
                Toast.makeText(this, "Login Succesfull", Toast.LENGTH_SHORT).show()
                checkLoginState()
            }
            else{
                Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}