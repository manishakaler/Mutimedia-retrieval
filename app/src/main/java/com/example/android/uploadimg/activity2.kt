package com.example.android.uploadimg


import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.nkzawa.socketio.client.Socket



private val newsocket: socket = socket()
private var mSocket: Socket = newsocket.getSocket()

class activity2 : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity2)

        title = "group4"

        ProgressDialog.show(this, "Loading...", "Getting data from server")

        val startingIntent = intent
        var imageName = startingIntent.getStringExtra("Image")
        //  val img:Uri = Uri.parse(imagePath)
        // val imagePath1 = convertToBase64(img)
        connect_socket(imageName)
    }

    /*   private fun convertToBase64(imagePath: Uri): String {

           val bm = MediaStore.Images.Media.getBitmap(this.contentResolver, imagePath)//BitmapFactory.decodeFile(imagePath)

           val baos = ByteArrayOutputStream()

           bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)

           val byteArrayImage = baos.toByteArray()

           return Base64.encodeToString(byteArrayImage, Base64.DEFAULT)

       }
   */
    fun connect_socket(imageName: String) {

        mSocket.connect()
        mSocket.emit("hello", imageName)

        mSocket.on("hello"){args ->
            val data = args[0] as String

            val intent = Intent(this, DisplayImages::class.java)
            intent.putExtra("imageList", data)
            startActivity(intent)
        }


    }
}



