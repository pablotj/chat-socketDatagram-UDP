package app

import java.io.Serializable
import java.util.*

data class Message(var userName: String, var message: String, var date: Date, var hostname: String, var host: Int, var status: Boolean): Serializable{
    override fun toString(): String {
        return "$userName on $date \n$message\n"
    }
    fun toStringOther():String{
        return "$userName $message\n"
    }
}