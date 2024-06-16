package com.example.notesappwithfirebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class MyCustomAdapter(private val data: ArrayList<DataModel>) : RecyclerView.Adapter<MyCustomAdapter.MyViewHolder>() {

    class MyViewHolder(itemview:View):RecyclerView.ViewHolder(itemview){
        val title = itemview.findViewById<TextView>(R.id.title)
        val note = itemview.findViewById<TextView>(R.id.note)
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
    }


}
