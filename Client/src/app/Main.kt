package app

import java.util.*
import javax.swing.JOptionPane

object Main{

    @JvmStatic
    fun main(args: Array<String>) {
        val serverHostname= "192.168.0.4"
        val serverPort= 8008
       // val session = JOptionPane.showInputDialog("WELCOME TO OPEN CHAT\n Enter a username to enter the chat")
        val session = (Random().nextInt(999999999-111111111 ) + 111111111).toString()
        Socket(session,serverHostname,serverPort ).start()

    }
}