package com.example.chat_app

import android.content.Context
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val messagelist:ArrayList<message>) :RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    val ITEM_RECEIVE=1
    val ITEM_SENT=2;



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType==1){
            //inflate recieve
            val view:View= LayoutInflater.from(context).inflate(R.layout.receive,parent,false)
            return ReceivetViewHolder(view)
        }else{
           //inflate sent
            val view:View=LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
            return SentViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage=messagelist[position]

        if(holder.javaClass==SentViewHolder::class.java){

            //logic for sent view holder
            val viewHolder=holder as SentViewHolder

            holder.SentMessage.text=currentMessage.message
        }else{
            //do stuff for receive view holder
            val viewHolder=holder as ReceivetViewHolder
            holder.ReceiveMessage.text=currentMessage.message

        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage=messagelist[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.sender)){
            return ITEM_SENT
        }else{
            return ITEM_RECEIVE
        }
    }

    override fun getItemCount(): Int {
        return messagelist.size

    }
    class SentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {

        val SentMessage=itemView.findViewById<TextView>(R.id.txt_sentmessage)
    }
    class ReceivetViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {

        val ReceiveMessage=itemView.findViewById<TextView>(R.id.receivemessagetxt)
    }

}