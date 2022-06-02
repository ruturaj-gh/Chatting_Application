package com.example.chat_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Chatactivity : AppCompatActivity() {
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageBox:EditText
    private lateinit var sendButton:ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messagList: ArrayList<message>
    private lateinit var mDbRef: DatabaseReference
    var receiverRoom:String?=null
    var senderRoom:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatactivity)

        val name=intent.getStringExtra("name")
        val receiveruid=intent.getStringExtra("uid")
        val senderUid=FirebaseAuth.getInstance().currentUser?.uid

        mDbRef=FirebaseDatabase.getInstance().getReference()

        senderRoom=receiveruid + senderUid
        receiverRoom=senderUid+ receiveruid

        supportActionBar?.title=name
        chatRecyclerView=findViewById(R.id.chatRecyclerView)
        messageBox=findViewById(R.id.messagebox)
        sendButton=findViewById(R.id.sendButton)
        messagList= ArrayList()
        messageAdapter= MessageAdapter(this,messagList)
        chatRecyclerView.layoutManager=LinearLayoutManager(this)
        chatRecyclerView.adapter=messageAdapter

        //logic for adding data to recyclerview
        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messagList.clear()
                    for(postSnapshot in snapshot.children){
                        val message=postSnapshot.getValue(message::class.java)
                        messagList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            }
            )


        //adding the message to the databasee

        sendButton.setOnClickListener{
            val message=messageBox.text.toString()
            val messageObject=message(message,senderUid)
            mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbRef.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)

                }
            //after message sending set back to default
            messageBox.setText("")



        }





    }
}