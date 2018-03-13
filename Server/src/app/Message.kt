package app

import java.io.Serializable
import java.util.*

data class Message(var userName: String, var message: String, var date: Date, var hostname: String, var host: Int,  var status: Boolean): Serializable{
    override fun toString(): String {

        return if(status){
            "$userName on $date \n$message"
        }else{
            "$userName $message"
        }
    }
}