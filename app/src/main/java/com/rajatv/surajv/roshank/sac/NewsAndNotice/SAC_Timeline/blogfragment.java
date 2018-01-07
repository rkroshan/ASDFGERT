package com.rajatv.surajv.roshank.sac.NewsAndNotice.SAC_Timeline;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.storage.OnProgressListener;
import com.rajatv.surajv.roshank.sac.NewsAndNotice.NewsAndNotice;
import com.rajatv.surajv.roshank.sac.R;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.rajatv.surajv.roshank.sac.home.Home;

import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * Created by CREATOR on 10/27/2017.
 */

public class blogfragment extends AppCompatActivity implements View.OnClickListener {

    private static final int READ_STORAGE = 1;
    private static final int PICK_IMAGE = 2;
    private static final String POST_TITLE = "postTitle";
    private static final String POST_DESC = "postDescription";
    private static final String IMAGE_URI = "imageUri";
    private SharedPreferences sharedPreferences;
    ImageView imageButton;
    private View blog_back;
    EditText title,description;
    Button submit;
    Uri uri=null;

    private ProgressDialog progressDialog;

    DatabaseReference database;
    StorageReference storage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_frag);

        sharedPreferences = getSharedPreferences(StringVariable.SHAREDPREFERENCE_STUDENTDATA,0);

        imageButton = (ImageView) findViewById(R.id.imageButton);
        title = (EditText) findViewById(R.id.post_title);
        description = (EditText) findViewById(R.id.post_description);
        submit = (Button) findViewById(R.id.submit_post);
        progressDialog = new ProgressDialog(this);
        imageButton.setOnClickListener(this);
        submit.setOnClickListener(this);

        blog_back = (View) findViewById(R.id.blog_back);
        blog_back.setOnClickListener(this);

        //firebase instances
        database = FirebaseDatabase.getInstance().getReference().child("Timeline");
        storage = FirebaseStorage.getInstance().getReference().child("Timeline");
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.imageButton:
                check_permission();
                break;
            case R.id.submit_post:
                submit_post();
                break;
            case R.id.blog_back:
                finish();
                break;
        }

    }

    private void submit_post() {
        final DatabaseReference mdatabaseref = database.push();
        String title_data = title.getText().toString();
        Date date = new Date();
        String description_data = description.getText().toString();
        if(title_data.isEmpty()){
            Toast.makeText(this,"Post Title is required",Toast.LENGTH_SHORT).show();
        }else if(uri==null){
            Toast.makeText(this,"Please Select an Image",Toast.LENGTH_SHORT).show();
        }else {
            progressDialog.setMessage("Uploading....");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
            mdatabaseref.child(POST_TITLE).setValue(title_data);
            mdatabaseref.child(StringVariable.STUDENTDATA_ADMIN_NAME).setValue(sharedPreferences.getString(StringVariable.STUDENTDATA_ADMIN_NAME,""));
            mdatabaseref.child(StringVariable.STUDENTDATA_POST).setValue(sharedPreferences.getString(StringVariable.STUDENTDATA_POST,""));
            mdatabaseref.child(StringVariable.STUDENTDATA_POST_TIME).setValue(date.toString());
            mdatabaseref.child(POST_DESC).setValue(description_data);
            storage.child(title_data+uri.getLastPathSegment()).putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    @SuppressWarnings("VisibleForTests") Uri uri=taskSnapshot.getDownloadUrl();
                    mdatabaseref.child(IMAGE_URI).setValue(uri.toString());
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Post Submitted",Toast.LENGTH_SHORT).show();
                    switchFragment();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage("Uploaded "+(int)progress+"%");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Uploading failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void check_permission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            loadimage();
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},READ_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==READ_STORAGE && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            loadimage();
        }else {
            Toast.makeText(this,"Permission_Denied",Toast.LENGTH_SHORT).show();
            switchFragment();
        }
    }

    private void loadimage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK){
            uri = data.getData();
            imageButton.setImageURI(uri);
        }
    }

    private void switchFragment() {
        startActivity(new Intent(this,NewsAndNotice.class));
    }
}
