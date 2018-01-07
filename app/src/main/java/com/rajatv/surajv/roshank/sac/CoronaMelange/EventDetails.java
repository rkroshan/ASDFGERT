package com.rajatv.surajv.roshank.sac.CoronaMelange;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajatv.surajv.roshank.sac.DataStore.DataStore;
import com.rajatv.surajv.roshank.sac.MainActivity;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static java.security.AccessController.getContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventDetails extends AppCompatActivity implements View.OnClickListener {

    TextView event_details_text;
    Button register;

    SharedPreferences sharedpref,sharedprefStudent;
    String data,college,rollno;
    String eventdata;
    String alleventData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_event_details);
        data = getIntent().getStringExtra("Eventdata");
        event_details_text = (TextView) findViewById(R.id.event_details_text);
        event_details_text.setText(DataStore.getData(data));
        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(this);

        sharedpref = getSharedPreferences(StringVariable.SHAREDPREFERENCE_STUDENTEVENTDATA, Context.MODE_PRIVATE);
        sharedprefStudent=getSharedPreferences(StringVariable.SHAREDPREFERENCE_STUDENTDATA,Context.MODE_PRIVATE);
        college = sharedprefStudent.getString(StringVariable.STUDENTDATA_COLLEGE,"");
        rollno = sharedprefStudent.getString(StringVariable.STUDENTDATA_ROLLNO,"");
        registerAction();
    }

    private void registerAction() {
        SharedPreferences sharedpref = getSharedPreferences(StringVariable.SHAREDPREFERENCE_STUDENTEVENTDATA, Context.MODE_PRIVATE);
        eventdata = sharedpref.getString(data,"0");

        if(eventdata.equals(data)){
            Log.e(data +" Value",eventdata);
            register.setBackgroundResource(R.drawable.submit_post_btn_green);
            Log.e("registerBtn Background","Unchanged");
            register.setText("Unregister");
            Log.e("registerButton Text","Unregister");
        }else {
            Log.e(data +" Value",eventdata);
            register.setText("Register");
            Log.e("registerButton Text","Register");
            register.setBackgroundResource(R.drawable.submit_post_btn);
            Log.e("registerButton Backgund","changed");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register:
                SharedPreferences sharedpref = getSharedPreferences(StringVariable.SHAREDPREFERENCE_STUDENTEVENTDATA, Context.MODE_PRIVATE);
                eventdata = sharedpref.getString(data,"");
                Log.e(data +" Value",eventdata);
                if(eventdata.equals(data)) showalert(0);
                else showalert(1);
        }
    }

    private void showalert(int i) {
        String message;
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this,R.style.myDialog));
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
            Log.e("showalert(0)"," goes to unreigstration");
            message = "Are you sure you wanna Unregister ? ";
            builder.setMessage(message)
                    .setPositiveButton("Unregister", new DialogInterface.OnClickListener() {
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
        }
    }

    private void updateEventData(final int i) {

        final DatabaseReference dataref = FirebaseDatabase.getInstance().getReference().child("College").child(college).child(rollno).child("Events");
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
            Log.e("EventDetails 1", alleventData + "----");
            alleventData += ":sac:" + data;

            dataref.setValue(alleventData).addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        register.setBackgroundResource(R.drawable.submit_post_btn_green);
                        register.setText("Unregister");

                        editor.putString(data, data);
                        Log.e("Data Value Changed: ", data);
                        editor.apply();
                    } else {
                        Toast.makeText(getApplicationContext(), "Sorry Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Log.e("EventDetails 0", alleventData + "----");
            if (!alleventData.isEmpty()) {
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
                        register.setBackgroundResource(R.drawable.submit_post_btn);
                        register.setText("Register");
                        editor.putString(data, "0");
                        Log.e("Data Value Changed: ", data);
                        editor.apply();
                    } else {
                        Toast.makeText(getApplicationContext(), "Sorry Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
