package com.shrinkcom.videocreate.fragment;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.shrinkcom.videocreate.R;
import com.shrinkcom.videocreate.activities.EditVideoActivity;
import com.shrinkcom.videocreate.activities.HorizontalListView;
import com.shrinkcom.videocreate.adapter.Adapter_VideoFolder;
import com.shrinkcom.videocreate.adapter.GalleryAdapter;
import com.shrinkcom.videocreate.adapter.StudioAdapter;
import com.shrinkcom.videocreate.adapter.StudioRecyclerAdapter;
import com.shrinkcom.videocreate.interfaces.RecycleItemClickListener;
import com.shrinkcom.videocreate.model.Model_Video;
import com.shrinkcom.videocreate.utils.PathUtil;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;

public class PhotoFragment extends Fragment {
    StudioAdapter mAdapter;
    private HorizontalListView gvGallery;
    boolean isMultiSelect = false;
    List<Uri> multiselect_list;
    ActionMode mActionMode;
    int PICK_IMAGE_MULTIPLE = 1000;
    List<String> imagesEncodedList;
    ArrayList<Uri> mArrayUri;
    private String path;
    private byte[] data_file;
    private List<byte[]> files_bytes;
    private List<String> pathList;
    String imageEncoded, POISubCategoriesname, POISubCategoriesnameGerman;
    GalleryAdapter galleryAdapter;
    Menu context_menu;
    ArrayList<MediaStore.Video> videoList;

    static Integer[] drawableArray = {R.drawable.slide_one, R.drawable.slide_two, R.drawable.slide_three,
            R.drawable.slide_four};
    Adapter_VideoFolder obj_adapter;
    ArrayList al_video = new ArrayList<>();
    RecyclerView recyclerViewnew;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    private static final int REQUEST_PERMISSIONS = 100;
    public PhotoFragment() {
        // Required empty public constructor
    }


    public static PhotoFragment newInstance(String param1, String param2) {
        PhotoFragment fragment = new PhotoFragment();
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
        View view= inflater.inflate(R.layout.fragment_photo, container, false);
        gvGallery = view.findViewById(R.id.gv);
        mArrayUri = new ArrayList<Uri>();
        pathList = new ArrayList<>();
        files_bytes = new ArrayList<>();
        mAdapter = new StudioAdapter(getActivity(), new RecycleItemClickListener() {
            @Override
            public void onItemClick(int postion, int type) {
                Intent intent=new Intent(getActivity(), EditVideoActivity.class);
                startActivity(intent);
            }
        });

        recyclerViewnew = (RecyclerView) view.findViewById(R.id.recycler_view1);
        recyclerViewLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerViewnew.setLayoutManager(recyclerViewLayoutManager);

        fn_checkpermission();

        gvGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (isMultiSelect)
                    multi_select(i);
                else
                    Toast.makeText(getApplicationContext(), "Details Page", Toast.LENGTH_SHORT).show();
            }
        });

        gvGallery.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (!isMultiSelect) {
                    multiselect_list = new ArrayList<Uri>();
                    isMultiSelect = true;

                    if (mActionMode == null) {
                        mActionMode = getActivity().startActionMode(mActionModeCallback);

                    }
                }

                multi_select(i);
                return false;
            }
        });
        return view;
    }



    private void fn_checkpermission(){
        /*RUN TIME PERMISSIONS*/

        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        }else {
            Log.e("Else","Else");
            fn_video();
        }
    }




    public void fn_video() {

        int int_position = 0;
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name,column_id,thum;

        String absolutePathOfImage = null;
        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Video.Media.BUCKET_DISPLAY_NAME,MediaStore.Video.Media._ID,MediaStore.Video.Thumbnails.DATA};

        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);
        column_id = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
        thum = cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA);

        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            Log.e("Column", absolutePathOfImage);
            Log.e("Folder", cursor.getString(column_index_folder_name));
            Log.e("column_id", cursor.getString(column_id));
            Log.e("thum", cursor.getString(thum));

            Model_Video obj_model = new Model_Video();
            obj_model.setBoolean_selected(false);
            obj_model.setStr_path(absolutePathOfImage);
            obj_model.setStr_thumb(cursor.getString(thum));

            al_video.add(obj_model);

        }


        obj_adapter = new Adapter_VideoFolder(getApplicationContext(),al_video,getActivity());
        recyclerViewnew.setAdapter(obj_adapter);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    fn_video();
                } else {
                    Toast.makeText(getActivity(), "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
                }
            }
        }
    }




    void getImageFromGalary() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // When an ImageallEvent is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
                // Get the ImageallEvent from data

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                imagesEncodedList = new ArrayList<String>();
                if (data.getData() != null) {

                    if (mArrayUri != null)
                        mArrayUri.clear();


                    Uri mImageUri = data.getData();
                    if (mArrayUri != null)
                        mArrayUri.clear();

                    mArrayUri.add(mImageUri);
                    try {
                        path = PathUtil.getPath(getActivity(), mImageUri);
                        data_file = PathUtil.readBytesFromFile(path);
                        files_bytes.add(data_file);
                        pathList.add(path);
                        Log.d("PATH", ">>" + files_bytes);
                        Log.d("URIS", mImageUri.toString());
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }


                    Cursor cursor = getActivity().getContentResolver().query(mImageUri, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded = cursor.getString(columnIndex);
                    imagesEncodedList.add(imageEncoded);

                    cursor.close();

                    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                    mArrayUri.add(mImageUri);


                    galleryAdapter = new GalleryAdapter(getApplicationContext(), mArrayUri, multiselect_list);
                    gvGallery.setAdapter(galleryAdapter);


                } else {
                    if (data.getClipData() != null) {

                        if (mArrayUri != null)
                            mArrayUri.clear();

                        ClipData mClipData = data.getClipData();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);

                            try {
                                path = PathUtil.getPath(getActivity(), uri);
                                data_file = PathUtil.readBytesFromFile(path);
                                files_bytes.add(data_file);
                                pathList.add(path);
                                Log.d("PATH", path);
                                Log.d("URIS", uri.toString());
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }


                            if (pathList != null) {
                                Log.d("OK", String.valueOf(files_bytes.size()));
                            }

                            Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();

                            galleryAdapter = new GalleryAdapter(getApplicationContext(), mArrayUri, multiselect_list);
                            gvGallery.setAdapter(galleryAdapter);
                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                        Log.v("LOG_TAG", "Selected Images path" + mArrayUri.toString());
                        Log.v("LOG_TAG", "Selected Images path" + imagesEncodedList.toString());
                    }
                }
            } else {
                Toast.makeText(getActivity(), "You haven't picked ImageallEvent", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.d("EXCEPTIONPRINF&", ">>" + e);
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_multi_select, menu);
            context_menu = menu;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            isMultiSelect = false;
            multiselect_list = new ArrayList<Uri>();

            refreshAdapter();
        }
    };

    public void refreshAdapter() {
        galleryAdapter.multiselect_list = multiselect_list;
        galleryAdapter.multiselect_list = mArrayUri;
        galleryAdapter.notifyDataSetChanged();
    }

    public void multi_select(int position) {
        if (mActionMode != null) {
            if (multiselect_list.contains(mArrayUri.get(position)))
                multiselect_list.remove(mArrayUri.get(position));
            else
                multiselect_list.add(mArrayUri.get(position));

            if (multiselect_list.size() > 0)
                mActionMode.setTitle("" + multiselect_list.size());
            else
                mActionMode.setTitle("");

            refreshAdapter();

        }
    }
}
