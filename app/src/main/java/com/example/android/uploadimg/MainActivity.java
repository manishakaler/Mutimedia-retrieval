package com.example.android.uploadimg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity extends Activity {


    private static final int Img_Capture_Code = 11;
   // private ImageButton mCam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);


        //mCam =(ImageButton)findViewById(R.id.imageButton);



           // public void onClick(View v) {
             //   Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
             //   startActivityForResult(cInt,Img_Capture_Code);
           // }
        //});
    }

    public void Floorplan(View view) {
        Intent fp = new Intent(this, FloorplanActivity.class);
        startActivity(fp);
    }

    public void ImageRetrieval(View view) {
        Intent ir = new Intent(this, ImageActivity.class);
        startActivity(ir);
    }


}

