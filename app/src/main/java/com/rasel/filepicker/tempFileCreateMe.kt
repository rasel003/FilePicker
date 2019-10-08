package com.rasel.filepicker

import android.content.Context
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


class tempFileCreateMe(
    inputStream: InputStream,
    applicationContext: Context
) {

   /* fun saveStreamTemp(fStream: InputStream): String {
        val file: File
        try {
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())

            file = File(con.getCacheDir(), "temp_$timeStamp.jpg")
            val output: OutputStream
            try {
                output = FileOutputStream(file)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                return ""
            }

            try {
                try {
                    val buffer = ByteArray(1024)
                    var read: Int

                    while ((fStream.read(buffer)) != -1) {
                        read = fStream.read(buffer)
                        output.write(buffer, 0, read)
                    }
                    output.flush()
                } finally {
                    output.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } finally {
            try {
                fStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
                return ""
            }

        }

        return file.getPath()
    }*/
}