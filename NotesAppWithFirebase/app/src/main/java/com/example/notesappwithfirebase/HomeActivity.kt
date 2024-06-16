package com.example.notesappwithfirebase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class HomeActivity : AppCompatActivity() {
    private var data= ArrayList<DataModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val addBtn=findViewById<FloatingActionButton>(R.id.floatingActionButton)
        addBtn.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,AddNote::class.java))
        })
        val recyclerView=findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        var db=Firebase.firestore
        db= FirebaseFirestore.getInstance()
        db.collection("Users").document("Notes").collection(FirebaseAuth.getInstance().currentUser?.uid.toString())
            .get().addOnSuccessListener(OnSuccessListener  { snapshot->
                for(document in snapshot.documents){
                    val value: DataModel? =document.toObject(DataModel::class.java)
                    Log.d("value",value.toString())
                    data.add(value!!)
                }
                Log.d("tag for firebase data",data.toString())
                recyclerView.adapter = MyCustomAdapter(data)
            })

    }
}