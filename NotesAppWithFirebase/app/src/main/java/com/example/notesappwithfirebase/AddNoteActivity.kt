package com.example.notesappwithfirebase

import android.content.Intent
import android.nfc.Tag
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.math.abs
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
fun randomtoken():String {
    val random = abs(Random(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)).nextLong())
    return random.toString()
}
class AddNote : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_note)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val title=findViewById<TextInputEditText>(R.id.title)
        val note=findViewById<TextInputEditText>(R.id.note)
        val nv=findViewById<MaterialToolbar>(R.id.navigationBar)
        nv.setNavigationOnClickListener(View.OnClickListener {
            finish()
        })
        nv.setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener {
            when(it.itemId){
                R.id.save->{
                   uploadToFirebase(title.text.toString(),note.text.toString())
                    true

                }

                else -> {
                  false
                }
            }
        })

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun uploadToFirebase(title:String,note:String) {
        val token = randomtoken()
        val user=FirebaseAuth.getInstance().currentUser

        val data= hashMapOf(
            "id" to token,
            "title" to title,
            "note" to note
        )
        val db=Firebase.firestore
        db.collection("Users").document("Notes").collection(user?.uid.toString()).document(token)
            .set(data).addOnCompleteListener(OnCompleteListener {
                if (it.isComplete){
                    Toast.makeText(this,"Note Added",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,HomeActivity::class.java))
                }else{
                    Toast.makeText(this,it.exception?.message.toString(),Toast.LENGTH_SHORT).show()
                }
            })

    }
}