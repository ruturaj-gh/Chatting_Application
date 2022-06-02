package com.example.chat_app

class message {
    var message:String?=null
    var sender:String?=null
    constructor(){

    }
    constructor(message:String?,senderId:String?){
        this.message=message
        this.sender=senderId
    }
}