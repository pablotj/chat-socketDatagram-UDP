package app

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

open class Serializable{

    fun serializable(obj: Message): ByteArray{
        val outByte = ByteArrayOutputStream()
        val outObj = ObjectOutputStream(outByte)
        outObj.writeObject(obj)
        return outByte.toByteArray()
    }

    fun deserialize(data: ByteArray): Any {
        val inByte = ByteArrayInputStream(data)
        val inObj = ObjectInputStream(inByte)
        return inObj.readObject()
    }

}
