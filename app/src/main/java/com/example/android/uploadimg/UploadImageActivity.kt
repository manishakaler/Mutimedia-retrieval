package com.example.android.uploadimg

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.opengl.ETC1.encodeImage
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.ImageButton
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_result.*
import org.jetbrains.anko.doAsync
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.net.Socket
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.BitmapFactory

import android.R.attr.bitmap
import android.R.attr.data
import android.support.v4.app.NotificationCompat.getExtras




class UploadImageActivity : Activity() {
  //  private var filePath: Uri? = null
    private val PICK_IMAGE_REQUEST = 999
    private val Img_Capture_Code = 11
   // private var mCam: ImageButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
      //  mCam = findViewById(R.id.imageButton) as ImageButton

        select.setOnClickListener { selectImage() }

     //  imageButton.setOnClickListener(View.OnClickListener {
       //     val cInt = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
         //   startActivityForResult(cInt, Img_Capture_Code)
        //})
    }


    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST  && resultCode == RESULT_OK && data != null && data.data != null)
        {
            val uri: Uri? = data.data
            Glide.with(this).load(uri).into(imagePreview)
            val imgStr = encodeImage(uri)

            Log.d("manisha", imgStr)

            doAsync { sendImage(imgStr) }

        }
     /*   if(requestCode == Img_Capture_Code && resultCode == RESULT_OK){
            val extras = data.getExtras()
            val imageBitmap = data.extras.get("data") as Bitmap
            imagePreview.setImageBitmap(imageBitmap)
            val baos = ByteArrayOutputStream()
            //  bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val imgStr = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
            Log.d("manisha", imgStr)

            doAsync { sendImage(imgStr) }

        } */
    }

    private fun sendImage(imgStr: String) {
        val ip = "172.30.8.74"
        val port = 5000

        val socket = Socket(ip, port)
        val dOut = DataOutputStream(socket.getOutputStream())
        //dOut.writeUTF(imgStr)
        dOut.writeBytes(imgStr)
        dOut.flush()
        socket.close()

        /*val mIn = BufferedReader(InputStreamReader(socket.getInputStream()))

        var message = ""
        var charsRead: Int
        val buffer = CharArray(2048)

        Log.d("manisha", "Hello: $message")


        Log.d("manisha", "Hello: ${mIn.readLine()}")

        charsRead = mIn.read(buffer)
        while (charsRead != -1) {

            Log.d("manisha", "Hello: $charsRead")
            message += String(buffer).substring(0, charsRead)
            charsRead = mIn.read(buffer)
        }

        Log.d("manisha", "Hello: $message")*/
    }

  //  public fun void cam() {
    //    val cInt = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
      //  startActivityForResult(cInt, Img_Capture_Code)
    //}

    private fun encodeImage(uri: Uri?): String {
        val bm = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }
}
