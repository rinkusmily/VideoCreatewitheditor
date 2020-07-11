package com.shrinkcom.videocreate.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shrinkcom.videocreate.R;
import com.shrinkcom.videocreate.interfaces.RecycleItemClickListener;

import java.util.List;
import java.util.StringTokenizer;

public class StudioAdapter extends RecyclerView.Adapter<StudioAdapter.MyViewHolder> {
    Context context;
    String description;
    RecycleItemClickListener recyclerListener;


    public StudioAdapter(Context context,RecycleItemClickListener recyclerListener) {
        this.context = context;
        this.recyclerListener = recyclerListener;

    }

    @Override
    public StudioAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_studio_adapter, parent, false);
        StudioAdapter.MyViewHolder vh = new StudioAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull StudioAdapter.MyViewHolder holder, final int position) {
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerListener.onItemClick(position, 0);
            }
        });


    }

    @Override
    public int getItemCount() {
        return 16;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_ads);


        }
    }

}
