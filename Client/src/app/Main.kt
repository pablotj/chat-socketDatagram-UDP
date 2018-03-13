package app

import javax.swing.JOptionPane

object Main{

    @JvmStatic
    fun main(args: Array<String>) {
        val serverHostname= "192.168.0.4"
        val serverPort= 8008
        val session = JOptionPane.showInputDialog("WELCOME TO OPEN CHAT\n Input your user name for began")
        Socket(session,serverHostname,serverPort ).start()

    }
}