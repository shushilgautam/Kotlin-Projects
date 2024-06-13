package com.example.notesappwithfirebase

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notesappwithfirebase.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        auth=FirebaseAuth.getInstance()
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.intentRegister.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,RegistrationActivity::class.java))
        })
        binding.login.setOnClickListener(View.OnClickListener {
            val email = binding.email.text.toString()
            val password =binding.password.text.toString()
            if (email.isNotEmpty() and password.isNotEmpty()){
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    if (it.isComplete){
                        Toast.makeText(this, "Login Succesfull ",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,HomeActivity::class.java))
                    }
                }
            }
        })
    }
}