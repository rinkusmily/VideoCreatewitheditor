package com.shrinkcom.videocreate.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shrinkcom.videocreate.R;

public class StudioRecyclerAdapter  extends RecyclerView.Adapter<StudioRecyclerAdapter.MyViewHolder> {
    Context context;
    Integer[] drawableArray;

    public StudioRecyclerAdapter(Context context, Integer[] drawableArray) {
        this.context = context;
        this.drawableArray = drawableArray;

    }

    @Override
    public StudioRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_studio_recycler_adapter, parent, false);
        StudioRecyclerAdapter.MyViewHolder vh = new StudioRecyclerAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull StudioRecyclerAdapter.MyViewHolder holder, final int position) {
//        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.placeholder(R.drawable.buzz_feed);
//        Glide.with(holder.imgselected.getContext())
//                .setDefaultRequestOptions(requestOptions)
//                .load(R.drawable.images)
//                .into(holder.imgselected);

    }

    @Override
    public int getItemCount() {
        return 8;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgselected;

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
