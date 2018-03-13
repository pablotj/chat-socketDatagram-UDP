package app

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.InetSocketAddress
import java.util.*

class Socket(var nameUser: String,var serverHostname: String, var serverPort: Int): Thread() {

    var ui: View? = null
    private var datagramSocket: DatagramSocket? = null
    private var addr1: InetSocketAddress? = null
    var port: Int? = null
    var hostname: String? = null

    init {
        connect()
        receive()
        ui = View(this@Socket)
        send(app.Message(nameUser,"has just joined the group",Date(),hostname!!,port!!,false))
    }

    /**
     * Open socket
     */
    private fun connect() {
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
    private fun receive () {
        Thread(Runnable {

            run {
                var x: Message

                while (true) {

                    val messageInfo = ByteArray(20000)

                    val datagrama1 = DatagramPacket(messageInfo, 700)
                    datagramSocket!!.receive(datagrama1)

                    x = Serializable().deserialize(messageInfo) as Message

                    ui!!.write(x)

                }
            }
        }).apply {
            start()
        }
    }
    /**
     * Send Message to Server
     */
    fun send(message: Message) {
        Thread(Runnable {
            run{
                try {

                    val messageInfo = Serializable().serializable(message)
                    val addr2 = InetAddress.getByName(serverHostname)
                    val datagrama2 = DatagramPacket(messageInfo, messageInfo.size, addr2, serverPort)
                    datagramSocket!!.send(datagrama2)
                } catch (e: Exception) {
                    println("Error al enviar el mensaje")
                }
            }
        }).apply {
            start()
            join()
            interrupt()
        }
    }

    /**
     * Close socket
     */
    fun close() {
        datagramSocket!!.close()
    }
}
