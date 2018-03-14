package app

import java.awt.*
import java.awt.event.*
import java.text.SimpleDateFormat
import javax.swing.*
import java.util.*
import javax.swing.JScrollPane
import javax.swing.JTextArea
import javax.swing.Spring.height
import com.sun.awt.SecurityWarning.getSize
import java.awt.Dimension






/**
 *
 * @author Pablo
 */
class View(var socket: Socket) : JFrame(){
    private var nameUser: String?= null
    private var jtConversation: JEditorPane? = null
    private var jsConversation: JScrollPane? = null
    private var jtMessage: JTextArea? = null
    private var jsMessage: JScrollPane? = null
    private var jbSend: JButton? = null

    init {
        nameUser = socket.nameUser
        components()
        window()
    }

    private fun components() {

        jtConversation = JEditorPane()
        jtConversation!!.setBounds(0, 0, 250, 400)
        jtConversation!!.isEditable = false

        //jtConversation!!.lineWrap = true
        jtConversation!!.font = Font("Optima", Font.PLAIN, 13)

        jsConversation= JScrollPane(jtConversation)
        jsConversation!!.setBounds(0, 0, 250, 300)
        jsConversation!!.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS

        add(jsConversation)

        jtMessage = JTextArea("Write your message ...")
        jtMessage!!.setBounds(0,300,200,50)
        jtMessage!!.font = Font("Optima", Font.PLAIN, 13)
        jtMessage!!.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                jtMessage!!.text=""
            }
        })
        jtMessage!!.lineWrap = true


        jsMessage= JScrollPane(jtMessage)
        jsMessage!!.setBounds(0, 300, 200, 50)
        jsMessage!!.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
        add(jsMessage)

        jbSend = JButton("Send")
        jbSend!!.setBounds(200,300,50,50)
        jbSend!!.addActionListener(ClickSend())
        add(jbSend)
        var jLabel = JLabel("User Name")
        jLabel.setBounds(0, 350, 100, 25)
        add(jLabel)
        var jTextField = JTextField()
        jTextField.setBounds(100, 350, 100, 25)
        jTextField.addActionListener { jTextField.text="" }
        add(jTextField)

        var jChange = JButton("SET")
        jChange.setBounds(200, 350, 50, 25)
        jChange.addActionListener { nameUser = jTextField.text
            title = "Chat of * ${nameUser!!.toUpperCase()} *"}
        add(jChange)


    }
    var x : String = ""
    private fun window(){
        defaultCloseOperation = JFrame.DO_NOTHING_ON_CLOSE
        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(windowEvent: WindowEvent?) {
                socket.send(Message(nameUser!!, "has left the group", Date(),socket.hostname!!, socket.port!!,false))
                System.exit(0)
            }
        })
        setSize(250, 400)
        setLocationRelativeTo(null)

        layout = null
        isResizable = false
        isVisible = true
        title = "Chat of * ${socket.nameUser.toUpperCase()} *"

    }
    fun write(message: Message){

        jtMessage!!.text = ""

        if(message.message=="has left the group" ||message.message=="has just joined the group"){
            jtConversation!!.contentType = "text/html"
            x+="<p style='width:200dp; color:gray; text-align:center;'><i><b>${message}</b><i></p>"
            jtConversation!!.text = x
            move()


        }else{
            if(socket.port!=message.host){
            jtConversation!!.contentType = "text/html"
            x+="<p style='width:200dp;'><b style='color:blue;'>${message.userName.toUpperCase()}</b> <a style='color:gray; '>at ${SimpleDateFormat("HH:mm dd/MM/yyyy").format(message.date)}</a></p> <p  style='color:blue; word-wrap: break-word;'>${message.message}</p>"
            jtConversation!!.text = x
            move()}

            else{
                jtConversation!!.contentType = "text/html"
                x+="<p style='width:200dp; text-align:right;'><b style='color:green;'>${message.userName.toUpperCase()}</b> <a style='color:gray; '>at ${SimpleDateFormat("HH:mm dd/MM/yyyy").format(message.date)}</a></p> <p style='color:green; text-align:right; word-wrap: break-word;'>${message.message}</p>"
                jtConversation!!.text = x
                move()
            }
        }
    }
fun move(){

    val tamanhoTextArea = jtConversation!!.size
    val p = Point(
            0,
            tamanhoTextArea.height)
    jsConversation!!.viewport.viewPosition = p
}

    internal inner class ClickSend : ActionListener {

        override fun actionPerformed(e: ActionEvent) {

             socket.send(Message(nameUser!!,jtMessage!!.text, Date(),socket.hostname!!, socket.port!!,true))
        }
    }


}
