package com.example.android.uploadimg;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;

import com.bumptech.glide.Glide;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;


    public ImageAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v);
    }




    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);
        }
    }


        @Override
        public void onBindViewHolder(ImageViewHolder holder, int position) {
            final Upload uploadCurrent = mUploads.get(position);
            //imageView = itemView.findViewById(R.id.image_view_upload);

            holder.textViewName.setText(uploadCurrent.getName());
            Glide.with(mContext)
                    .load(uploadCurrent.getUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .fitCenter()
                    .centerCrop()
                    .into(holder.imageView);

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ResultActivity.class);
                    intent.putExtra("url", uploadCurrent.getUrl());
                    mContext.startActivity(intent);
                }
            });

            // Glide.with(context).load(upload.getUrl()).into(holder.imageView);
        }

}