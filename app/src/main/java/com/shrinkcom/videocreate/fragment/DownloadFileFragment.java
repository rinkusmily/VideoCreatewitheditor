package com.shrinkcom.videocreate.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shrinkcom.videocreate.R;


public class DownloadFileFragment extends Fragment {


    public DownloadFileFragment() {
        // Required empty public constructor
    }


    public static DownloadFileFragment newInstance(String param1, String param2) {
        DownloadFileFragment fragment = new DownloadFileFragment();
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
        return inflater.inflate(R.layout.fragment_download_file, container, false);
    }
}
