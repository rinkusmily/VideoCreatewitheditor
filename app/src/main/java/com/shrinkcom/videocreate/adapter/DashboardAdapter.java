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
import com.shrinkcom.videocreate.DashboardActivity;
import com.shrinkcom.videocreate.R;

import java.util.List;
import java.util.StringTokenizer;

public class DashboardAdapter  extends RecyclerView.Adapter<DashboardAdapter.MyViewHolder> {
    Context context;
    Integer[] drawableArray;

    public DashboardAdapter(Context context, Integer[] drawableArray) {
        this.context = context;
        this.drawableArray = drawableArray;

    }

    @Override
    public DashboardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_dashboard_adapter, parent, false);
        DashboardAdapter.MyViewHolder vh = new DashboardAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardAdapter.MyViewHolder holder, final int position) {
          RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.buzz_feed);
            Glide.with(holder.img.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(R.drawable.image)
                    .into(holder.img);

    }

    @Override
    public int getItemCount() {
        return 4;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_ads);
        }
    }
}
