package com.shrinkcom.videocreate.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.shrinkcom.videocreate.R;

import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter extends BaseAdapter {
 
    private Context ctx;
    private int pos;
    private LayoutInflater inflater;
    private ImageView ivGallery;
    ArrayList<Uri> mArrayUri;
    public List<Uri> multiselect_list = new ArrayList<>();
    public GalleryAdapter(Context ctx, ArrayList<Uri> mArrayUri, List<Uri> multiselect_list) {
 
        this.ctx = ctx;
        this.mArrayUri = mArrayUri;
        this.multiselect_list = multiselect_list;
    }
 
    @Override
    public int getCount() {
        return mArrayUri.size();
    }
 
    @Override
    public Object getItem(int position) {
        return mArrayUri.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return 0;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
 
        pos = position;
        inflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
        View itemView = inflater.inflate(R.layout.imv_poi_layout, parent, false);
 
        ivGallery = (ImageView) itemView.findViewById(R.id.ivGallery);
        Log.e("DSDFDSDFFSDSDDFS",">>"+mArrayUri.get(position));
        ivGallery.setImageURI(mArrayUri.get(position));

        return itemView;
    }
 
 
}