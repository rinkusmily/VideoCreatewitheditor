package com.shrinkcom.videocreate.fragment;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shrinkcom.videocreate.R;

import java.io.File;


public class MyFilesFragment extends Fragment {
     int reqCode=101;

    public MyFilesFragment() {
        // Required empty public constructor
    }


    public static MyFilesFragment newInstance(String param1, String param2) {
        MyFilesFragment fragment = new MyFilesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_files, container, false);

        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Audio "), reqCode);
        return view;
    }
}
