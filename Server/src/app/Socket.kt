package app

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.InetSocketAddress
import java.util.HashSet



class Socket(var hostname: String, var port: Int){

    var historic: ArrayList<Int> = ArrayList()
    var datagramSocket: DatagramSocket? = null
    var addr1: InetSocketAddress? = null


    init {
        connect()
        receive()
    }

    fun send(message: Message){
        val noDuplicates = HashSet<Int>()
        noDuplicates.addAll(historic)
        historic.clear()
        historic.addAll(noDuplicates)

        Thread(Runnable {

            run {

                for (item in historic) {

                    try {
                        val messageInfo = Serializable().serializable(message)

                        val addr2 = InetAddress.getByName(hostname)

                        val datagrama2 = DatagramPacket(messageInfo, messageInfo.size, addr2, item)
                        datagramSocket!!.send(datagrama2)
                    } catch (e: Exception) {
                        println("Error al enviar el mensaje")


                }}
            }
        }).apply {
            start()
            join()
            interrupt()

        }
    }

    fun receive(){

        val tReceive = Thread(Runnable {

            run{

                var x: Message

                while (true) {

                    val messageInfo = ByteArray(20000)

                    val datagrama1 = DatagramPacket(messageInfo, 700)
                    datagramSocket!!.receive(datagrama1)

                    x = Serializable().deserialize(messageInfo) as Message

                    historic.add(x.host)
                    send(x)

                    Thread.sleep(10)
                }

            }
        })
        tReceive.start()
    }

    fun connect(){

        this.port = port
        this.hostname = hostname

        addr1 = InetSocketAddress(hostname, port!!)
        datagramSocket = DatagramSocket(addr1)
    }
}