package com.rajatv.surajv.roshank.sac.CoronaMelange;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;

/**
 * Created by CREATOR on 11/17/2017.
 */

public class EventDetail_new extends AppCompatActivity implements View.OnClickListener {

    TextView eventdetail_subevent_name,eventdetail_event_name,eventdetail_about;
    Button eventdetails_eventletter_button,event_register_button;
    String data,clubname="";
    ScrollView event_detail_scrollview;

    Typeface type, type1;
    private String eventdata="";
    private String alleventData="";
    private String college;
    private String rollno;
    private String branch;
    private String name;
    private boolean registered;
    private boolean tcfidGenerated;
    private SharedPreferences sharedpref,sharedprefStudent;
    private DatabaseReference databaseReference_event_registration;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.event_details_new);

        type = Typeface.createFromAsset(getAssets(), "fonts/century_gothic.TTF");
        type1 = Typeface.createFromAsset(getAssets(), "fonts/calibrilight.ttf");

        init();
    }

    private void registerAction() {
        SharedPreferences sharedpref = getSharedPreferences(StringVariable.SHAREDPREFERENCE_STUDENTEVENTDATA, Context.MODE_PRIVATE);
        eventdata = sharedpref.getString(data,"0");

        if(eventdata.equals(data)){
            Log.e(data +" Value",eventdata);
            event_register_button.setBackgroundColor(ContextCompat.getColor(this,R.color.color2));
            Log.e("registerBtn Background","Unchanged");
            event_register_button.setText("Registered.");
            Log.e("registerButton Text","Registered.");
        }else {
            Log.e(data +" Value",eventdata);
            event_register_button.setText("Register");
            Log.e("registerButton Text","Register");
            event_register_button.setBackgroundColor(ContextCompat.getColor(this,R.color.WHITE));
            Log.e("registerButton Backgund","changed");
        }
    }

    private void init() {
        event_detail_scrollview = (ScrollView) findViewById(R.id.event_detail_scrollview);
        eventdetail_subevent_name = (TextView) findViewById(R.id.eventdetail_subevent_name);
        eventdetail_event_name = (TextView) findViewById(R.id.eventdetail_event_name);
        eventdetail_about = (TextView) findViewById(R.id.eventdetail_about);
        eventdetails_eventletter_button = (Button) findViewById(R.id.eventdetails_eventletter_button);
        event_register_button = (Button) findViewById(R.id.event_register_button);
        progressDialog = new ProgressDialog(this);
        
        event_register_button.setOnClickListener(this);

        eventdetail_subevent_name.setTypeface(type);
        eventdetail_event_name.setTypeface(type1);

        data = getIntent().getStringExtra("Eventdata");
        try {
            clubname = getIntent().getStringExtra("EventClub");
            setBackground(clubname);
        }catch (Exception e){}
        eventdetail_subevent_name.setText(data);
        eventdetail_event_name.setText(clubname);
        eventdetails_eventletter_button.setText(String.valueOf(data.charAt(0)));
        eventdetail_about.setText(eventsData.getEventdata(data));
        sharedpref = getSharedPreferences(StringVariable.SHAREDPREFERENCE_STUDENTEVENTDATA, Context.MODE_PRIVATE);
        sharedprefStudent=getSharedPreferences(StringVariable.SHAREDPREFERENCE_STUDENTDATA,Context.MODE_PRIVATE);
        college = sharedprefStudent.getString(StringVariable.STUDENTDATA_COLLEGE,"");
        rollno = sharedprefStudent.getString(StringVariable.STUDENTDATA_ROLLNO,"");
        name = sharedprefStudent.getString(StringVariable.STUDENTDATA_NAME,"");
        branch = sharedprefStudent.getString(StringVariable.STUDENTDATA_BRANCH,"");
        registered = sharedprefStudent.getBoolean(StringVariable.REGISTERED,false);
        tcfidGenerated = sharedprefStudent.getBoolean(StringVariable.TCFID_GENERATED, false);
        databaseReference_event_registration = FirebaseDatabase.getInstance().getReference().child(StringVariable.EVENT_REGISTRATION).child(data);
        registerAction();
    }

    private void setBackground(String data1) {
        if(data1!=null) {
            Log.e("Clubname",data1.toUpperCase());
            switch (data1.toUpperCase()) {
                case "CONCRETE":
                case "CONCREATE":
                    event_detail_scrollview.setBackgroundResource(R.mipmap.conl);
                    break;
                case "BYTEWORLD":
                case "BYTE WORLD":
                    event_detail_scrollview.setBackgroundResource(R.mipmap.byl);
                    break;
                case "ROBOTICS":
                    event_detail_scrollview.setBackgroundResource(R.mipmap.robol);
                    break;
                case "OHM":
                    event_detail_scrollview.setBackgroundResource(R.mipmap.ohml);
                    break;
                case "AAYAM":
                    event_detail_scrollview.setBackgroundResource(R.mipmap.aayaml);
                    break;
                case "YANTRIKI":
                    event_detail_scrollview.setBackgroundResource(R.mipmap.yantrikil);
                    break;
                case "ABHINAY":
                    event_detail_scrollview.setBackgroundResource(R.mipmap.abhinayl);
                    break;
                case "AVLOKAN":
                    event_detail_scrollview.setBackgroundResource(R.mipmap.avll);
                    break;
                case "NRITYANGANA":
                    event_detail_scrollview.setBackgroundResource(R.mipmap.nirtl);
                    break;
                case "KALAKRITI":
                    event_detail_scrollview.setBackgroundResource(R.mipmap.kalal);
                    break;
                case "SANHITA":
                    event_detail_scrollview.setBackgroundResource(R.mipmap.sanhital);
                    break;
                case "RAAGA":
                    event_detail_scrollview.setBackgroundResource(R.mipmap.raagal);
                    break;
                case "PRATIBIMB":
                    event_detail_scrollview.setBackgroundResource(R.mipmap.pral);
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.event_register_button:
                if(registered && tcfidGenerated) {
                    SharedPreferences sharedpref = getSharedPreferences(StringVariable.SHAREDPREFERENCE_STUDENTEVENTDATA, Context.MODE_PRIVATE);
                    eventdata = sharedpref.getString(data, "");
                    Log.e(data + " Value", eventdata);
                    if (eventdata.equals(data)) showalert(0);
                    else showalert(1);
                }else if(registered) {
                    showalert(-2);
                }
                else{
                    showalert(-1);
                }
                break;
        }
    }

    private void showalert(int i) {
        String message;
        @SuppressLint("RestrictedApi") AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this,R.style.myDialog));
        builder.setTitle("Registration");
        if(i==1){
            Log.e("showalert(1)"," goes to reigstration");
            message = "Are you Sure You wanna Register ? ";
            builder.setMessage(message)
                    .setPositiveButton("Register", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            updateEventData(1);
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            }).show();
        }
        else if(i==0){
            Log.e("showalert(0)"," goes to unregistration");
            message = "Are you sure you wanna UnRegister. ? ";
            builder.setMessage(message)
                    .setPositiveButton("UnRegister.", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            updateEventData(0);
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            }).show();
        }else if(i==-1){
            Log.e("showalert(-1)"," goes to unreigstration");
            message = "Register Yourself First and generate TCFID";
            builder.setMessage(message)
                    .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    }).show();
        }
        else if(i==-2){
            message = "Generate TCFID first";
            builder.setMessage(message).setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            }).show();
        }
    }

    private void updateEventData(final int i) {
        progressDialog.setMessage("Connecting To Server...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        final DatabaseReference dataref = FirebaseDatabase.getInstance().getReference().child(StringVariable.STUDENTDATA_COLLEGE).child(college).child(rollno).child(StringVariable.STUDENTDATA_EVENTS);
        dataref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot == null) {
                    alleventData = "";
                } else {
                    alleventData = dataSnapshot.getValue().toString();
                }
                Log.e("EventDetails", alleventData + "----");
                finalupdate(i,dataref);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void finalupdate(int i, DatabaseReference dataref) {
        final SharedPreferences.Editor editor = sharedpref.edit();
        if (i == 1) {
            progressDialog.setMessage("Registering...");
            Log.e("EventDetails 1", alleventData + "----");
            alleventData += ":sac:" + data;

            dataref.setValue(alleventData).addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        event_register_button.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color2));
                        event_register_button.setText("Registered");

                        editor.putString(data, data);
                        Log.e("Data Value Changed: ", data);
                        editor.apply();
                        Event_Registration_Update(1);
                    } else {
                        Toast.makeText(getApplicationContext(), "Sorry Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            progressDialog.setMessage("Unregistering...");
            Log.e("EventDetails 0", alleventData + "----");
            if (!alleventData.equals("")) {
                String[] events = alleventData.split(":sac:");
                alleventData = "";
                for (int j = 1; j < events.length; j++) {
                    if (!events[j].equals(data)) {
                        alleventData += ":sac:" + events[j];
                    }
                }
            }
            dataref.setValue(alleventData).addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        event_register_button.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.WHITE));
                        event_register_button.setText("Register");
                        editor.putString(data, "0");
                        Log.e("Data Value Changed: ", data);
                        editor.apply();
                        Event_Registration_Update(0);
                    } else {
                        Toast.makeText(getApplicationContext(), "Sorry Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void Event_Registration_Update(int i) {
        if(i==1){
            progressDialog.setMessage("Please Wait...");
            databaseReference_event_registration.child(rollno).child(StringVariable.STUDENTDATA_NAME).setValue(name);
            databaseReference_event_registration.child(rollno).child(StringVariable.STUDENTDATA_ROLLNO).setValue(rollno);
            databaseReference_event_registration.child(rollno).child(StringVariable.STUDENTDATA_BRANCH).setValue(branch);
            databaseReference_event_registration.child(rollno).child(StringVariable.STUDENTDATA_COLLEGE).setValue(college).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Registered",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            progressDialog.setMessage("Please Wait");
            databaseReference_event_registration.child(rollno).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"UnRegistered",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
