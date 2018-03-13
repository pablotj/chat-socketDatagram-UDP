package app

import java.net.DatagramSocket
import java.net.InetSocketAddress
import javax.swing.JOptionPane
import java.net.InetAddress




object Main{


    var port: Int? = null
    var hostname: String? = null

    @JvmStatic
    fun main(args: Array<String>) {
        port = 8008
        hostname = InetAddress.getLocalHost().hostAddress
        Socket(hostname!!, port!!)

        JOptionPane.showMessageDialog(null,
                "Your chat server is set up in:\n " +
                        "$hostname:$port\n" +
                        "modify the client so that there is communication.")

    }
}
