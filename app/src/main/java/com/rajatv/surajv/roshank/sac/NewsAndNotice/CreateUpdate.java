package com.rajatv.surajv.roshank.sac.NewsAndNotice;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.rajatv.surajv.roshank.sac.R;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Random;

import static android.app.Activity.RESULT_OK;

public class CreateUpdate extends Fragment implements View.OnClickListener {

    private static final int READ_REQUEST = 11;
    private static final int GET_FILE = 12;
    EditText heading,news;
    ImageView upload;
    RecyclerView upload_recyclerview;
    Button notify;
    ArrayList<Uri_List> mlist = new ArrayList<>();
    uploadfile_apdapter fileapdapter;

    private ProgressDialog progressdialog;

    //here comes the dragon
    DatabaseReference databaseref;
    StorageReference storageref;

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null){
            view = inflater.inflate(R.layout.fragment_create_update,container,false);

            databaseref = FirebaseDatabase.getInstance().getReference().child("News");
            storageref = FirebaseStorage.getInstance().getReference().child("News");

            init(view);
        }
        return view;
    }

    private void init(View v) {
        heading = (EditText) v.findViewById(R.id.heading);
        news = (EditText) v.findViewById(R.id.news);
        upload = (ImageView) v.findViewById(R.id.upload);
        upload_recyclerview = (RecyclerView) v.findViewById(R.id.upload_recyclerview);
        notify = (Button) v.findViewById(R.id.notify);
        progressdialog = new ProgressDialog(getContext());

        upload_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        fileapdapter= new uploadfile_apdapter(getContext(),mlist);
        upload_recyclerview.setAdapter(fileapdapter);
        //set listener
        upload.setOnClickListener(this);
        notify.setOnClickListener(this);

        ItemTouchHelper.SimpleCallback simpleItemtouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mlist.remove(viewHolder.getAdapterPosition());
                fileapdapter.notifyDataSetChanged();
            }
        };

        ItemTouchHelper itemtouchhelper = new ItemTouchHelper(simpleItemtouchCallback);
        itemtouchhelper.attachToRecyclerView(upload_recyclerview);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.upload:
                checkpermission();
                break;
            case R.id.notify:
                upload_and_notify();
        }
    }

    private void upload_and_notify() {
        String title = heading.getText().toString();
        String body = news.getText().toString();
        final DatabaseReference mdataref = databaseref.push();
        int i=0;
        Uri_List uridata = new Uri_List();
        Random rand = new Random();

        if(title.isEmpty()){
            Toast.makeText(getContext(),"Heading is Required",Toast.LENGTH_SHORT).show();
        }else if(body.isEmpty()){
            Toast.makeText(getContext(),"Description is Required",Toast.LENGTH_SHORT).show();
        }else {
            progressdialog.setMessage("Creating Update...");
            progressdialog.setCanceledOnTouchOutside(false);
            progressdialog.setCancelable(false);
            progressdialog.show();
            final StorageReference mstorageref = storageref.child(title + " " + body);
            mdataref.child("heading").setValue(title);
            mdataref.child("news").setValue(body);
            Log.e("Update",title+" "+body);
            if(mlist.size()!=0){
                for(i=0;i<mlist.size();i++){
                    uridata = mlist.get(i);
                    mstorageref.child(uridata.getUriFile_name()).putFile(uridata.getUri())
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           @SuppressWarnings("VisibleForTests") Uri uri=taskSnapshot.getDownloadUrl();
                            Log.e("Image Upload",uri.toString());
                            mdataref.child("images").push().setValue(uri.toString());
                          //  Log.e("images-----------",uri.toString());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
                progressdialog.dismiss();
                switchfragment();
            }else {
                progressdialog.dismiss();
                switchfragment();
            }
        }
    }

    private void switchfragment() {
                getActivity().startActivity(new Intent(getContext(),NewsAndNotice.class));
            }

    private void checkpermission() {
        if(ContextCompat.checkSelfPermission(getContext(),Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            uploadfile();
        }
        else {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},READ_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==READ_REQUEST && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            Log.e("Permission-->", ""+PackageManager.PERMISSION_GRANTED);
            uploadfile();
        }else {
            Toast.makeText(getContext(),"Permission Denied",Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadfile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent,GET_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GET_FILE && resultCode==RESULT_OK){

            Uri_List uridata1 = new Uri_List();
            uridata1.setUri(data.getData());
            uridata1.setUriFile_name(data.getData().getLastPathSegment());
            mlist.add(uridata1);
            Toast.makeText(getContext(), ""+data.getData(),Toast.LENGTH_SHORT).show();
            fileapdapter.notifyDataSetChanged();
        }
        else {
            Toast.makeText(getContext(), "Error getting file", Toast.LENGTH_SHORT).show();
        }
    }
}
