package com.example.android.uploadimg;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View;
import android.graphics.Color;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;


public class ResultActivity extends Activity {
    ImageView im1;
    ImageView im2;
    ImageView im3;
    ImageView im4;
    Button b1;
    private Bitmap operation;
    private Bitmap bmp1;
    private Bitmap bmp2;
    private Bitmap bmp3;
    private Bitmap bmp4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        b1 = (Button) findViewById(R.id.onClick);

        im1 = (ImageView) findViewById(R.id.imageView);
        im2=(ImageView) findViewById(R.id.imageView2);
        im3=(ImageView) findViewById(R.id.imageView3);
        im4=(ImageView) findViewById(R.id.imageView4);

        BitmapDrawable abmp1 = (BitmapDrawable) im1.getDrawable();
        bmp1 = abmp1.getBitmap();
        BitmapDrawable abmp3 = (BitmapDrawable) im2.getDrawable();
        bmp3 = abmp3.getBitmap();
        BitmapDrawable abmp4 = (BitmapDrawable) im3.getDrawable();
        bmp4 = abmp4.getBitmap();

        BitmapDrawable abmp2 = (BitmapDrawable) im4.getDrawable();
        bmp2 = abmp2.getBitmap();


        String url = getIntent().getStringExtra("url");

        Glide.with(this)
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .fitCenter()
                //.centerCrop()
                .into(im1);


    }
    /** Called when the user touches the button */
    public void sendMessage(View view) {
        // Do something in response to button click
        operation=Bitmap.createBitmap(bmp1.getWidth(),bmp1.getHeight(), bmp1.getConfig());
        for(int i=0; i<bmp1.getWidth(); i++){
            for(int j=0; j<bmp1.getHeight(); j++){
                int p = bmp1.getPixel(i, j);
                int r = Color.red(p);
                int g = Color.green(p);
                int b = Color.blue(p);
                int alpha = Color.alpha(p);

                r = 100  +  r;
                g = 100  + g;
                b = 100  + b;
                alpha = 100 + alpha;
                operation.setPixel(i, j, Color.argb(alpha, r, g, b));
            }
        }
        im2.setImageBitmap(operation);
    }
    public void sendMessage2(View view) {
        operation = Bitmap.createBitmap(bmp2.getWidth(),bmp2.getHeight(),bmp2.getConfig());

        for(int i=0; i<bmp2.getWidth(); i++){
            for(int j=0; j<bmp2.getHeight(); j++){
                int p = bmp2.getPixel(i, j);
                int r = Color.red(p);
                int g = Color.green(p);
                int b = Color.blue(p);
                int alpha = Color.alpha(p);

                r =  r + 150;
                g =  0;
                b =  0;
                alpha = 0;
                operation.setPixel(i, j, Color.argb(Color.alpha(p), r, g, b));
            }
        }
        im3.setImageBitmap(operation);
    }
    public void sendMessage3(View view) {
        operation = Bitmap.createBitmap(bmp3.getWidth(), bmp3.getHeight(), bmp3.getConfig());
        for (int i = 0; i < bmp3.getWidth(); i++) {
            for (int j = 0; j < bmp3.getHeight(); j++) {
                int p = bmp3.getPixel(i, j);
                int r = Color.red(p);
                int g = Color.green(p);
                int b = Color.blue(p);
                int alpha = Color.alpha(p);

                r = r - 50;
                g = g - 50;
                b = b - 50;
                alpha = alpha - 50;
                operation.setPixel(i, j, Color.argb(Color.alpha(p), r, g, b));
            }
        }
        im4.setImageBitmap(operation);
    }


}
