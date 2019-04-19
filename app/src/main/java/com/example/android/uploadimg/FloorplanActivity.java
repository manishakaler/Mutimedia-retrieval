package com.example.android.uploadimg;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;


public class FloorplanActivity extends Activity {
    ImageView imageView;
    Uri ImageUri;
    private static final int PICK_IMAGE = 100;
    String imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floorplan);

        imageView = findViewById(R.id.i1);
        Button button = findViewById(R.id.b1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //  ImageView imageView = findViewById(R.id.i1);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            ImageUri = data.getData();
            Log.d("Suthar", ImageUri.toString());
            imageView.setImageURI(ImageUri);
            File f = new File(ImageUri.getPath());
            imageName = f.getName();
            Intent sendStuff = new Intent(this, activity2.class);
            sendStuff.putExtra("Image", imageName);
            sendStuff.putExtra("imageName", getImageName(ImageUri));
            startActivity(sendStuff);
        }

    }

    private String getImageName(Uri uri){
        String[] projection = {MediaStore.Images.Media.DISPLAY_NAME};
        Cursor cursor=managedQuery(uri,projection,null,null,null);
        int column_index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
