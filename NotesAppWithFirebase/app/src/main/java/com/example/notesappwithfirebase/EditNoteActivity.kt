package com.example.notesappwithfirebase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class EditNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_note)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val originalTitle=intent.getStringExtra("title")
        val originalNOte=intent.getStringExtra("note")
        val token = intent.getStringExtra("id")
        val title=findViewById<TextInputEditText>(R.id.title)
        val note=findViewById<TextInputEditText>(R.id.note)
        val nv=findViewById<MaterialToolbar>(R.id.navigationBar)
        title.setText(originalTitle)
        note.setText(originalNOte)
        Log.d("idOfFirestore",token.toString())
        nv.setNavigationOnClickListener(View.OnClickListener {
            finish()
        })
        nv.setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener {
            when(it.itemId){
                R.id.save->{
                    edit_to_firebase(title.text.toString(),note.text.toString(), token.toString())
                    true

                }

                else -> {
                    false
                }
            }
        })
        val delBtn=findViewById<ExtendedFloatingActionButton>(R.id.delete)
        delBtn.setOnClickListener(View.OnClickListener {
            var db=Firebase.firestore
            db= FirebaseFirestore.getInstance()
            db.collection("Users").document("Notes").collection(FirebaseAuth.getInstance().currentUser?.uid.toString())
                .document(token.toString()).delete().addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(this,"Note Deleted",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,HomeActivity::class.java))
                    }else{
                        Toast.makeText(this,it.exception?.message.toString(),Toast.LENGTH_SHORT).show()
                    }
                }
        })
    }

    private fun edit_to_firebase(title: String, note: String,token :String) {
        val user= FirebaseAuth.getInstance().currentUser

        val data= hashMapOf(
            "id" to token,
            "title" to title,
            "note" to note
        )
        val db= Firebase.firestore
        db.collection("Users").document("Notes").collection(user?.uid.toString()).document(token)
            .set(data).addOnCompleteListener(OnCompleteListener {
                if (it.isComplete){
                    Toast.makeText(this,"Note Added", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,HomeActivity::class.java))
                }else{
                    Toast.makeText(this,it.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                }
            })
    }
}