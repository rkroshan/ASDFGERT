package com.rajatv.surajv.roshank.sac.dashboard;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.content.ContentResolver;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rajatv.surajv.roshank.sac.CoronaMelange.CoronaMelange;
import com.rajatv.surajv.roshank.sac.MainActivity;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.Signup.SignUp1;
import com.rajatv.surajv.roshank.sac.StringVariable;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static java.security.AccessController.getContext;

/**
 * Created by CREATOR on 11/10/2017.
 */

public class Profile extends Fragment {

    private LinearLayout profile_linearlayout,register_popup_linearlayout;
    private Button register_button;

    private static final int READ_REQUEST = 11;
    private static final int GET_FILE = 12;

    SharedPreferences pref,pref1, pref_profile_pic;
    TextView dashboard_name,dashboard_college,dashboard_branch,dashboard_year,dashboard_contact,dashboard_email;
    Button dashboard_tcfId,dashboard_eventRegistered;
    ImageView profile_pic;

    Bitmap profile_pic_bitmap;

    Typeface type, type1, type2, type3;

    public Profile() {
        // Required empty public constructor
    }


    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.profile, container, false);
            init(view);
        }
        return view;
    }


    private void init(View v) {
        profile_pic = v.findViewById(R.id.profile_pic);
        dashboard_name = v.findViewById(R.id.dashboard_name);
        dashboard_college = v.findViewById(R.id.dashboard_college);
        dashboard_branch = v.findViewById(R.id.dashboard_branch);
        dashboard_year = v.findViewById(R.id.dashboard_year);
        dashboard_contact = v.findViewById(R.id.dashboard_contact);
        dashboard_email = v.findViewById(R.id.dashboard_email);
        dashboard_tcfId = v.findViewById(R.id.dashboard_tcfId);
        dashboard_eventRegistered = v.findViewById(R.id.dashboard_eventRegistered);
        pref = getActivity().getSharedPreferences(StringVariable.SHAREDPREFERENCE_STUDENTDATA,0);
        pref1 = getActivity().getSharedPreferences(StringVariable.SHAREDPREFERENCE_STUDENTEVENTDATA,0);
        pref_profile_pic = getActivity().getSharedPreferences(StringVariable.SHAREDPREFERENCE_STUDENTDATA_PROFILE_PIC,0);
        profile_linearlayout = v.findViewById(R.id.profile_linearlayout);
        register_popup_linearlayout = v.findViewById(R.id.register_popup_linearlayout);
        register_button = v.findViewById(R.id.register_button);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SignUp1.class));

            }
        });

        if(pref.getBoolean(StringVariable.REGISTERED,false)){
            profile_linearlayout.setVisibility(View.VISIBLE);
            register_popup_linearlayout.setVisibility(View.GONE);
        }else {
            profile_linearlayout.setVisibility(View.GONE);
            register_popup_linearlayout.setVisibility(View.VISIBLE);
        }

        String uriData = pref_profile_pic.getString(StringVariable.SHAREDPREFERENCE_STUDENTDATA_PROFILE_PIC, "");
        if(uriData != null)
            getUriData(uriData);

       profile_pic.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               checkpermission();
           }
       });


        type = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/typo_style_regular_demo.otf");
        type1 = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/century_gothic.TTF");
        type2 = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/calibrilight.ttf");
        type3 = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/mayeka_bold_eventfirstletter.otf");

        dashboard_tcfId.setTypeface(type);
        dashboard_name.setTypeface(type1);
        dashboard_college.setTypeface(type2);
        dashboard_branch.setTypeface(type2);
        dashboard_year.setTypeface(type2);
        dashboard_contact.setTypeface(type2);
        dashboard_email.setTypeface(type2);
        dashboard_eventRegistered.setTypeface(type3);

        setdata();

    }


    private void checkpermission() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this.getActivity());
        builder.setIcon(R.mipmap.londa);
        builder.setTitle("Change profile pic..");

        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    uploadfile();
                }
                else {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},READ_REQUEST);
                }
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }


    public void uploadfile()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,GET_FILE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GET_FILE && resultCode == RESULT_OK && data != null)
        {

            String profile_pic_uri = data.getData().toString();

            SharedPreferences.Editor editor = pref_profile_pic.edit();
            editor.putString(StringVariable.SHAREDPREFERENCE_STUDENTDATA_PROFILE_PIC, profile_pic_uri);
            editor.apply();
            getUriData(profile_pic_uri);

        }
    }
    public void getUriData(String uriData)
    {
        Uri uri = Uri.parse(uriData);
        try {
            ContentResolver contentResolver = Profile.this.getActivity().getContentResolver();
            InputStream inputStream = contentResolver.openInputStream(uri);
            profile_pic_bitmap = BitmapFactory.decodeStream(inputStream);
            setImages();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void setImages()
    {
        profile_pic.setImageBitmap(profile_pic_bitmap);



    }

    private void setdata() {
        dashboard_name.setText(pref.getString(StringVariable.STUDENTDATA_NAME,""));
        dashboard_college.setText(pref.getString(StringVariable.STUDENTDATA_COLLEGE,""));
        dashboard_branch.setText(pref.getString(StringVariable.STUDENTDATA_BRANCH,""));
        String year = pref.getString(StringVariable.STUDENTDATA_YEAR,"");
        if(year.equals("1"))
            year="1st";
        else if(year.equals("2"))
            year="2nd";
        else
            year += "th";
        if (!year.equals("th")) {
            dashboard_year.setText(year + " " + "year");
        }else{
            dashboard_year.setText("");
        }
        dashboard_contact.setText(pref.getString(StringVariable.STUDENTDATA_MOBILENO,""));
        dashboard_email.setText(pref.getString(StringVariable.STUDENTDATA_EMAILID,""));
        if (pref.getString(StringVariable.STUDENTDATA_ADMIN_POST,StringVariable.STUDENTDATA_POST_COORDINATOR).equals(StringVariable.STUDENTDATA_POST_PI)){
            dashboard_tcfId.setVisibility(View.GONE);
        }else {
            String tcfId = pref.getString(StringVariable.STUDENTDATA_TCFID, "");
            if(tcfId == null || tcfId=="") {
                dashboard_tcfId.setText("Generate TCF ID");
                dashboard_tcfId.setTextSize(18);
                dashboard_tcfId.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getContext(), CoronaMelange.class));
                    }
                });
            }
            else
            dashboard_tcfId.setText(tcfId);
        }
        dashboard_eventRegistered.setText(data());
    }

    private String data() {
        int count=0;
        Log.e("Registered : ","setData---");
        Map<String, ?> eventsData = pref1.getAll();
        for(Map.Entry<String,?> event:eventsData.entrySet()){
            Log.e("Registered : ",event.getKey());
            if(event.getValue()!="0"){
                count++;
            }
        }
        return String.valueOf(count);
    }
}
