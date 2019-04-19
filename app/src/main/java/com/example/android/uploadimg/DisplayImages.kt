package com.example.android.uploadimg

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_display_images.*


class DisplayImages : AppCompatActivity() {

    // Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_images)



        val data = intent.getStringExtra("imageList")
        Log.d("Manisha", data.toString());

        val lines: List<String> = data.split("\n")

        val dirPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).absolutePath + "/floorplans/"
        // val dirPath = "./storage/emulated/0/DCIM/"
        //Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)

        Glide.with(this).load(dirPath + lines[0].substring(0, lines[0].length)).into(imageView1)
        Glide.with(this).load(dirPath + lines[1].substring(0, lines[1].length)).into(imageView2)
        Glide.with(this).load(dirPath + lines[2].substring(0, lines[2].length)).into(imageView3)
        Glide.with(this).load(dirPath + lines[3].substring(0, lines[3].length)).into(imageView4)
        Glide.with(this).load(dirPath + lines[4].substring(0, lines[4].length)).into(imageView5)
        Glide.with(this).load(dirPath + lines[5].substring(0, lines[5].length)).into(imageView6)

    }


}
