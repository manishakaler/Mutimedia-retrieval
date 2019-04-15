package com.example.android.uploadimg;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.PipedInputStream;
import java.util.HashMap;
import java.util.Objects;


public class DatabaseActivity extends Activity {

    private static final int PICK_IMAGE_REQUEST = 1;
 //   private static final int Image_Capture_Code = 11;
 //   private ImageButton mButtonCapture;
  //  private int val;

    private Button mButtonChooseImage;
    private ImageButton mButtonUpload;
    private EditText mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar mProgressBar;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask<UploadTask.TaskSnapshot> mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        mButtonChooseImage = findViewById(R.id.button_choose_image);
        mButtonUpload = findViewById(R.id.button_upload);
        mImageView = findViewById(R.id.image_view);
        mProgressBar = findViewById(R.id.progress_bar);


       // mButtonCapture =(ImageButton)findViewById(R.id.camera);
        mImageView = (ImageView) findViewById(R.id.image_view);
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");


        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(DatabaseActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });

       // mButtonCapture.setOnClickListener(new View.OnClickListener() {

           // public void onClick(View v) {
             //   Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
               // startActivityForResult(cInt,Image_Capture_Code);
           // }
        //});
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Glide.with(this).load(mImageUri).into(mImageView);
        }
       // if (requestCode == Image_Capture_Code) {
         //   if (resultCode == RESULT_OK) {
           //     val = Image_Capture_Code;
             //   Bitmap bp = (Bitmap) data.getExtras().get("data");
               // mImageView.setImageBitmap(bp);
               // encodeBitmapAndSaveToFirebase(bp);
               //mImageUri = data.getData();
                //Glide.with(this).load(mImageUri).into(mImageView);
            //}
            else {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }
        }


    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void uploadFile() {
        if (mImageUri != null ) {
            final StorageReference fileReference = mStorageRef.child("uploads" + System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri);


            Task<Uri> urlTask = mUploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw Objects.requireNonNull(task.getException());
                    }

                    // Continue with the task to get the download URL
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        Log.d("suthar", downloadUri.toString() + System.currentTimeMillis());

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mProgressBar.setProgress(0);
                            }
                        }, 500);


                        Toast.makeText(DatabaseActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                        HashMap<String, String> map = new HashMap<>();
                        map.put("url", downloadUri.toString());
                       // map.put("name", mEditTextFileName.getText().toString());
                        mDatabaseRef.push().setValue(map);

                    } else {
                        Toast.makeText(DatabaseActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            mUploadTask
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

   // public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
     //   ByteArrayOutputStream baos = new ByteArrayOutputStream();
       // bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
       // String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
       // DatabaseReference ref = FirebaseDatabase.getInstance()
         //       .getReference("uploads");

        //ref.setValue(imageEncoded);
    //}


}