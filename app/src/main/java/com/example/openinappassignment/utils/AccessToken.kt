package com.example.openinappassignment.utils

import android.content.Context
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class AccessToken(private val context: Context) {

    private val fileName = "token.txt"
    private val accessToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjU5MjcsImlhdCI6MTY3NDU1MDQ1MH0.dCkW0ox8tbjJA2GgUx2UEwNlbTZ7Rr38PVFJevYcXFI"


    fun setAccessToken(){
        val path = context.applicationContext.filesDir
        try {
            val writer = FileOutputStream(File(path,fileName))
            writer.write(accessToken.toByteArray(Charsets.UTF_8))
            writer.close()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun getAccessToken():String? {
        val path = context.applicationContext.filesDir
        val readFrom = File(path,fileName)
        val accessToken = ByteArray(readFrom.length().toInt())
        try {
            val fileInputStream = FileInputStream(readFrom)
            fileInputStream.read(accessToken)
            return String(accessToken)
        }catch (e: Exception){
            e.printStackTrace()
            return null
        }
    }

}