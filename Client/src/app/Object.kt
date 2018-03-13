package app

import java.io.Serializable
import java.util.*

data class Object(var userName: String, var message: String, var date: Date, var hostname: String, var host: Int): Serializable{
    override fun toString(): String {
        return "$userName on $date \n$message\n"
    }
    fun toStringOther():String{
        return "$userName $message\n"
    }
}