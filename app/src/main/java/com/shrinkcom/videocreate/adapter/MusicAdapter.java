package com.shrinkcom.videocreate.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.shrinkcom.videocreate.fragment.AllFragment;
import com.shrinkcom.videocreate.fragment.CameraFragment;
import com.shrinkcom.videocreate.fragment.DownloadFragment;
import com.shrinkcom.videocreate.fragment.FilesFragment;
import com.shrinkcom.videocreate.fragment.PhotoFragment;

public class MusicAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public MusicAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                DownloadFragment downloadFragment = new DownloadFragment();
                return downloadFragment;
            case 1:
                FilesFragment filesFragment = new FilesFragment();
                return filesFragment;

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
