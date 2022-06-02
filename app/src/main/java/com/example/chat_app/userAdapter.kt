package com.example.chat_app

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class userAdapter(val context:Context,val userlist:ArrayList<user>):RecyclerView.Adapter<userAdapter.userviewholder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userviewholder {
        val view:View=LayoutInflater.from(context).inflate(R.layout.userlayout,parent,false)
        return userviewholder(view)

    }




    override fun onBindViewHolder(holder: userviewholder, position: Int) {
        val currentuser=userlist[position]

        holder.textname.text=currentuser.name

        holder.itemView.setOnClickListener{
            val intent= Intent(context,Chatactivity::class.java)
            intent.putExtra("name",currentuser.name)
            intent.putExtra("uid",currentuser.uid)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return userlist.size

    }
    class userviewholder(itemView:View):RecyclerView.ViewHolder(itemView){

        val textname=itemView.findViewById<TextView>(R.id.txtname)
    }
}