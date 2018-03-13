package app

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.InetSocketAddress
import java.util.*

class Socket(var nameUser: String,var serverHostname: String, var serverPort: Int): Thread() {
    var ui: View? = null

    override fun run() {
        connect()
        listen().start()
        ui = View(this@Socket)
        send(app.Message(nameUser,"se acaba de unir al grupo",Date(),hostname!!,port!!,false))
    }

    private var datagramSocket: DatagramSocket? = null
    private var addr1: InetSocketAddress? = null


    var port: Int? = null
    var hostname: String? = null

    /**
     * Open socket
     */
    fun connect() {
        val to = 6000
        val from = 5000
        port = Random().nextInt(to - from) + from

        hostname = InetAddress.getLocalHost().hostAddress


        addr1 = InetSocketAddress(hostname, port!!)
        datagramSocket = DatagramSocket(addr1)

    }
    /**
     * Received Messages from Server
     */
    inner class listen : Thread() {
        override fun run() {

            var x: Message

            while (true) {

                val messageInfo = ByteArray(20000)

                val datagrama1 = DatagramPacket(messageInfo, 700)
                datagramSocket!!.receive(datagrama1)

                x = Serializable().deserialize(messageInfo) as Message

                ui!!.write(x)

            }
        }
    }
    /**
     * Send Message to Server
     */
    fun send(message: Message) {
        try {

            val messageInfo = Serializable().serializable(message)
            val addr2 = InetAddress.getByName(serverHostname)
            val datagrama2 = DatagramPacket(messageInfo, messageInfo.size, addr2, serverPort)
            datagramSocket!!.send(datagrama2)
        } catch (e: Exception) {
            println("Error al enviar el mensaje")
        }

    }

    /**
     * Close socket
     */
    fun close() {
        datagramSocket!!.close()
    }
}
