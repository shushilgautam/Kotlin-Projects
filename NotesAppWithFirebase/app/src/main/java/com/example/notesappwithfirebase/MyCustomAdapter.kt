package com.example.notesappwithfirebase

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.card.MaterialCardView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import java.util.ArrayList
import kotlin.random.Random

class MyCustomAdapter(private val data: ArrayList<DataModel>) : RecyclerView.Adapter<MyCustomAdapter.MyViewHolder>() {

    class MyViewHolder(itemview:View):RecyclerView.ViewHolder(itemview){
        val title = itemview.findViewById<TextView>(R.id.title)
        val note = itemview.findViewById<TextView>(R.id.note)
        val noteLayout = itemview.findViewById<LinearLayout>(R.id.noteLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemview= LayoutInflater.from(parent.context).inflate(R.layout.card_layout,parent, false)

        return MyViewHolder(itemview)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = data[position].title
        holder.note.text = data[position].note
        holder.noteLayout.setBackgroundColor(holder.itemView.resources.getColor(randomcolor(),null))
        holder.itemView.setOnClickListener(View.OnClickListener {
            val intent= Intent(holder.itemView.context,EditNoteActivity::class.java)
            intent.putExtra("title",data[position].title)
            intent.putExtra("id",data[position].id)
            intent.putExtra("note",data[position].note)
            holder.itemView.context.startActivity(intent)
        })

    }
    var random=-1
    private fun randomcolor():Int{
        val list = ArrayList<Int>()
        list.add(R.color.note1)
        list.add(R.color.note2)
        list.add(R.color.note3)
        list.add(R.color.note4)
        list.add(R.color.note5)
        list.add(R.color.note6)
        list.add(R.color.note7)
//        val seed = System.currentTimeMillis().toInt()
//        val random = Random(seed).nextInt(list.size)

        random++
        return list[random%list.size]
    }
}
