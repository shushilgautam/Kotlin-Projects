package com.example.notesappwithfirebase

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notesappwithfirebase.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth


class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegistrationBinding.inflate(layoutInflater)
        auth=FirebaseAuth.getInstance()
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.register.setOnClickListener(View.OnClickListener {
            val fullname=binding.fullName.text.toString()
            val email=binding.email.text.toString()
            val password =binding.password.text.toString()
            if (email.isNotEmpty() and password.isNotEmpty()){
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                    if (it.isComplete) {
                        Toast.makeText(this, "Registration Succesfull", Toast.LENGTH_SHORT).show()
                        val i = Intent(this, MainActivity::class.java)
                        startActivity(i)

                    }else {
                        Toast.makeText(this, it.exception?.message.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        })
    }
}