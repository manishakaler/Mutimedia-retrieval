package com.example.android.uploadimg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ImageActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_imageretrieval);

    }
    public void upData(View view) {
        Intent i = new Intent(this, DatabaseActivity.class);
        startActivity(i);
    }


    public void select(View view) {
        Intent in = new Intent(this, SelectActivity.class);
        startActivity(in);
    }

    public void srch(View view) {
        // startActivity(Intent(this, UploadImageActivity::class.java))
        Intent j = new Intent(this, UploadImageActivity.class);
        startActivity(j);
    }

}
