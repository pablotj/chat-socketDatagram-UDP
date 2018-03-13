package app

import java.awt.Color
import java.awt.Font
import java.awt.event.*
import javax.swing.*
import java.util.*
import javax.swing.JScrollPane
import javax.swing.JTextArea




/**
 *
 * @author Pablo
 */
class View(var trn: Socket) : JFrame(){

    private var jtConversation: JTextArea? = JTextArea()
    private var jsConversation: JScrollPane? = null
    private var jtMessage: JTextField? = null
    private var jbSend: JButton? = null



    init {

        init()

        defaultCloseOperation = JFrame.DO_NOTHING_ON_CLOSE
        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(windowEvent: WindowEvent?) {
                trn.send(Object(trn.nameUser, "ha salido del grupo", Date(),trn.hostname!!, trn.port!!))
                System.exit(0)
            }
        })
        setSize(500, 350)
        setLocationRelativeTo(null)
        layout = null
        isResizable = false
        isVisible = true
        title = "Chat of * ${trn.nameUser} *"



    }
    private fun init() {

        jtConversation = JTextArea()
        jtConversation!!.setBounds(0, 0, 500, 250)
        jtConversation!!.background = Color.white
        jtConversation!!.font = Font("Arial", Font.PLAIN, 13)

        jsConversation= JScrollPane(jtConversation)
        jsConversation!!.setBounds(0, 0, 500, 250)
        jsConversation!!.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
        add(jsConversation)

        jtMessage = JTextField("Write your message ...")
        jtMessage!!.setBounds(0,250,350,50)
        jtMessage!!.addActionListener (ClickSend())
        jtMessage!!.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                jtMessage!!.text = ""
            }
        })
        add(jtMessage)

        jbSend = JButton("Send")
        jbSend!!.setBounds(350,250,100,50)
        jbSend!!.addActionListener(ClickSend())
        add(jbSend)
    }

    fun write(message: Object){

        jtMessage!!.text = ""

        if(message.message=="ha salido del grupo" ||message.message=="se acaba de unir al grupo"){

            jtConversation!!.append(message.toStringOther())
        }else{
            jtConversation!!.append(message.toString())
        }


    }


    internal inner class ClickSend : ActionListener {

        override fun actionPerformed(e: ActionEvent) {

             trn.send(Object(trn.nameUser,jtMessage!!.text, Date(),trn.hostname!!, trn.port!!))
        }
    }


}
