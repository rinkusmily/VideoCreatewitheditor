package com.shrinkcom.videocreate.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shrinkcom.videocreate.R;
import com.shrinkcom.videocreate.interfaces.RecycleItemClickListener;

import java.io.IOException;
import java.util.List;

public class MyfilesAdapter extends RecyclerView.Adapter<MyfilesAdapter.MyViewHolder> {
        Context context;
    List<String> ListElementsArrayList ;

    RecycleItemClickListener recyclerListener;


public MyfilesAdapter(Context context,List<String> ListElementsArrayList,RecycleItemClickListener recyclerListener) {
        this.context = context;
        this.ListElementsArrayList = ListElementsArrayList;
        this.recyclerListener = recyclerListener;

        }

@Override
public MyfilesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_myfiles_adapter, parent, false);
    MyfilesAdapter.MyViewHolder vh = new MyfilesAdapter.MyViewHolder(v);
        return vh;
        }

@Override
public void onBindViewHolder(@NonNull final MyfilesAdapter.MyViewHolder holder, final int position) {
//        holder.img.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View v) {
//        recyclerListener.onItemClick(position, 0);
//        }
//        });
    final MediaPlayer mp=new MediaPlayer();
    holder.imgstop.setVisibility(View.GONE);

    try{
        //you can change the path, here path is external directory(e.g. sdcard) /Music/maine.mp3
        mp.setDataSource("http://www.hubharp.com/web_sound/BachGavotte.mp3");

        mp.prepare();
    }catch(Exception e){e.printStackTrace();}

    holder.imgplay.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mp.start();
            holder.imgplay.setVisibility(View.GONE);
            holder.imgstop.setVisibility(View.VISIBLE);

        }
    });
    holder.imgpause.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mp.pause();
        }
    });
    holder.imgstop.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mp.stop();
            holder.imgplay.setVisibility(View.VISIBLE);
            holder.imgstop.setVisibility(View.GONE);
        }
    });
    Log.e("Listsong","++"+ListElementsArrayList.get(position));

      holder.tvsongname.setText(ListElementsArrayList.get(position));
        }

@Override
public int getItemCount() {
        return ListElementsArrayList.size();
        }

public class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView imgplay,imgpause,imgstop;
    TextView tvsongname;

    public MyViewHolder(View itemView) {
        super(itemView);
        imgplay = itemView.findViewById(R.id.img_play);
        imgpause = itemView.findViewById(R.id.img_pause);
        imgstop = itemView.findViewById(R.id.img_stop);
        tvsongname = itemView.findViewById(R.id.tv_songs_name);


    }
}

}
