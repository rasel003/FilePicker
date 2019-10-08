package com.rasel.filepicker

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_PICTURES
import android.os.Environment.getExternalStoragePublicDirectory
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

private const val READ_REQUEST_CODE: Int = 42

class MainActivity : AppCompatActivity() {

    val TAG = "rsl"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnFilePicker.setOnClickListener {
            performFileSearch()
        }
    }


    /**
     * Fires an intent to spin up the "file chooser" UI and select an image.
     */
    fun performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            // Filter to only show results that can be "opened", such as a
            // file (as opposed to a list of contacts or timezones)
            addCategory(Intent.CATEGORY_OPENABLE)

            // Filter to show only images, using the image MIME data type.
            // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
            // To search for all documents available via installed storage providers,
            // it would be "*/*".
            type = "*/*"
        }

        startActivityForResult(intent, READ_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)


        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            resultData?.data?.also { uri ->
                Log.i(TAG, "Uri: $uri")
                //  showImage(uri)
                readTextFromUri(uri)
            }
        }
    }

    private fun readTextFromUri(uri: Uri) {

        contentResolver.openInputStream(uri)?.use { inputStream ->
            val path = saveStreamTemp(inputStream)
            Log.d("rsl", path)
        }
    }

    fun saveStreamTemp(fStream: InputStream): String {
        val file: File
        try {
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())

            // file = File(this.getCacheDir(), "temp_$timeStamp.jpeg")
            //  file = File( getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "temp_$timeStamp.jpeg")
            file = File(
                this.getExternalFilesDir(DIRECTORY_PICTURES)?.getAbsolutePath(),
                "temp_$timeStamp.jpeg"
            )
          Log.d("rsl", "path abs : ${file.absolutePath}")
            val output: OutputStream
            val outB : BufferedOutputStream
            try {
                output = FileOutputStream(file)
                outB = BufferedOutputStream(output)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                return ""
            }

            try {
                try {
                    val buffer = ByteArray(8192)
                    var read: Int

                    while ((fStream.read(buffer)) >= 0) {
                        read = fStream.read(buffer)
                        outB.write(buffer, 0, read)
                    }
                    outB.flush()
                } finally {
                    output.flush()
                    output.close()
                    outB.close()
                    fStream.close()
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
    }


}
