package com.shrinkcom.videocreate.adapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.content.Context;
import android.os.Bundle;

import com.shrinkcom.videocreate.R;
import com.shrinkcom.videocreate.fragment.AllFragment;
import com.shrinkcom.videocreate.fragment.CameraFragment;
import com.shrinkcom.videocreate.fragment.PhotoFragment;

public class MyAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public MyAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                CameraFragment cameraFragment = new CameraFragment();
                return cameraFragment;
            case 1:
                PhotoFragment photoFragment = new PhotoFragment();
                return photoFragment;
            case 2:
                AllFragment allFragment = new AllFragment();
                return allFragment;
            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}
