package com.shrinkcom.videocreate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.shrinkcom.videocreate.MainActivity;
import com.shrinkcom.videocreate.R;
import com.shrinkcom.videocreate.adapter.GridViewAdapter;
import com.shrinkcom.videocreate.fragment.PhotoFragment;
import com.shrinkcom.videocreate.model.Model_images;

import java.util.ArrayList;

public class PhotoActivity extends AppCompatActivity {
    int int_position;
    private GridView gridView;
    GridViewAdapter adapter;
    ArrayList<Model_images> al_menu = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        gridView = (GridView)findViewById(R.id.gv_folder);
        int_position = getIntent().getIntExtra("value", 0);
        adapter = new GridViewAdapter(this,al_menu,int_position);
        gridView.setAdapter(adapter);
    }
}
