package app

import java.io.Serializable
import java.util.*
import java.text.SimpleDateFormat

data class Message(var userName: String, var message: String, var date: Date, var hostname: String, var host: Int,  var status: Boolean): Serializable{
    override fun toString(): String {
        val dateFormat = SimpleDateFormat("HH:mm dd/MM/yyyy").format(date).split(" ")
        return if(status){
            "${userName.toUpperCase()} at ${dateFormat[0]} of ${dateFormat[1]} \n$message\n\n"
        }else{
            "${userName.toUpperCase()} $message\n\n"
        }
    }
}