package com.shrinkcom.videocreate.fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.shrinkcom.videocreate.R;
import com.shrinkcom.videocreate.adapter.MyfilesAdapter;
import com.shrinkcom.videocreate.interfaces.RecycleItemClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;


public class FilesFragment extends Fragment {
    RecyclerView recycler_music;
    Context context;
    public static final int RUNTIME_PERMISSION_CODE = 7;
    String[] ListElements = new String[] { };
    List<String> ListElementsArrayList ;
    ArrayAdapter<String> adapter ;
    ContentResolver contentResolver;
    Cursor cursor;
    Uri uri;
    MyfilesAdapter myfilesAdapter;


    public FilesFragment() {
        // Required empty public constructor
    }


    public static FilesFragment newInstance(String param1, String param2) {
        FilesFragment fragment = new FilesFragment();
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
        View view= inflater.inflate(R.layout.fragment_files, container, false);
        recycler_music=view.findViewById(R.id.recycler_my_files);
        context = getApplicationContext();

        ListElementsArrayList = new ArrayList<>(Arrays.asList(ListElements));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycler_music.setLayoutManager(mLayoutManager);
         myfilesAdapter=new MyfilesAdapter(getActivity(),ListElementsArrayList, new RecycleItemClickListener() {
           @Override
           public void onItemClick(int postion, int type) {

           //    Toast.makeText(getActivity(),parent.getAdapter().getItem(postion).toString(), Toast.LENGTH_LONG).show();

           }
       });

        recycler_music.setAdapter(myfilesAdapter);

       // adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, ListElementsArrayList);

        // Requesting run time permission for Read External Storage.
        AndroidRuntimePermission();
        GetAllMediaMp3Files();

      //  recycler_music.setAdapter(adapter);


        // ListView on item selected listener.
//        recycler_music.setOnItemClickListener(new AdapterView.OnItemClickListener()
//        {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                // TODO Auto-generated method stub
//                // Showing ListView Item Click Value using Toast.
//
//                Toast.makeText(getActivity(),parent.getAdapter().getItem(position).toString(), Toast.LENGTH_LONG).show();
//
//            }
//        });
        return view;
    }


    public void GetAllMediaMp3Files(){

        contentResolver = context.getContentResolver();

        uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        cursor = contentResolver.query(
                uri, // Uri
                null,
                null,
                null,
                null
        );

        if (cursor == null) {

            Toast.makeText(getActivity(),"Something Went Wrong.", Toast.LENGTH_LONG);

        } else if (!cursor.moveToFirst()) {

            Toast.makeText(getActivity(),"No Music Found on SD Card.", Toast.LENGTH_LONG);

        }
        else {

            int Title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);

            //Getting Song ID From Cursor.
            //int id = cursor.getColumnIndex(MediaStore.Audio.Media._ID);

            do {

                // You can also get the Song ID using cursor.getLong(id).
                //long SongID = cursor.getLong(id);

                String SongTitle = cursor.getString(Title);

                // Adding Media File Names to ListElementsArrayList.
                ListElementsArrayList.add(SongTitle);

            } while (cursor.moveToNext());
        }
    }

    // Creating Runtime permission function.
    public void AndroidRuntimePermission(){

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){

            if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){

                AlertDialog.Builder alert_builder = new AlertDialog.Builder(getActivity());
                alert_builder.setMessage("External Storage Permission is Required.");
                alert_builder.setTitle("Please Grant Permission.");
                alert_builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        ActivityCompat.requestPermissions(
                                getActivity(),
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                RUNTIME_PERMISSION_CODE

                        );
                    }
                });

                alert_builder.setNeutralButton("Cancel",null);

                AlertDialog dialog = alert_builder.create();

                dialog.show();

            }
            else {

                ActivityCompat.requestPermissions(
                        getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        RUNTIME_PERMISSION_CODE
                );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){

        switch(requestCode){

            case RUNTIME_PERMISSION_CODE:{

                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }
                else {

                }
            }
        }
    }


}
