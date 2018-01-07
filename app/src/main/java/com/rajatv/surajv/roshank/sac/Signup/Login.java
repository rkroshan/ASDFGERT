package com.rajatv.surajv.roshank.sac.Signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajatv.surajv.roshank.sac.MainActivity;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by CREATOR on 11/4/2017.
 */

public class Login extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences sharedPreferences;

    private EditText login_email,login_password;
    private Button login_btn;
    private TextView forgot_password;

    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private String userRoll;

    private HashMap<String,String > userdata = new HashMap<>();
    private String[] data;

    View focusView;


    private ProgressDialog progressdialog;
    private HashMap<String, String> user_Admindata1 = new HashMap<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e("LoginPage---","Started");
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_page);

       init();

    }

    private void init() {

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        login_email = (EditText) findViewById(R.id.login_email);
        login_password = (EditText) findViewById(R.id.login_password);

        login_btn = (Button) findViewById(R.id.login_btn);

        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/widolte-demo.regular-demo.otf");
        login_btn.setTypeface(type);

        forgot_password = (TextView) findViewById(R.id.forgot_password);

        forgot_password.setOnClickListener(this);
        login_btn.setOnClickListener(this);

        progressdialog = new ProgressDialog(this);

        sharedPreferences = getSharedPreferences(StringVariable.SHAREDPREFERENCE_STUDENTDATA,MODE_PRIVATE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_btn:
                AuthStart();
                break;
            case R.id.forgot_password:
                sendResetPassword();
                break;
        }
    }

    private void sendResetPassword() {
        Log.i("Login","sendResetPassword");
        final String email = login_email.getText().toString();
        Log.i("Login","User Email: "+email);
        boolean cancel = false;

        if(email.isEmpty())
        {
            cancel = true;
            login_email.setBackgroundResource(R.drawable.edittext_bg_required);
            login_email.setError("Enter registered email.");
            focusView = login_email;
            focusView.requestFocus();
        }
        else if(!email.contains("@") || !email.contains("."))
        {
            cancel = true;
            login_email.setBackgroundResource(R.drawable.edittext_bg_required);
            login_email.setError("Enter valid email address.");
            focusView = login_email;
            focusView.requestFocus();
        }
        if (!cancel) {
            auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Log.i("Login","Email Send To : "+email);
                        Toast.makeText(getApplicationContext(),"Email Send To : "+email,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void AuthStart() {
        Log.i("Login","---AuthStart---");
        progressdialog.setMessage("Logging In...");
        progressdialog.setCancelable(false);
        progressdialog.setCanceledOnTouchOutside(false);
        final String email = login_email.getText().toString();
        String password = login_password.getText().toString();
        boolean cancel = false;

        if(password.length()==0)
        {
            cancel = true;
            login_password.setError("Password length must be at least 8");
            focusView = login_password;
            focusView.requestFocus();
        }
        if(email.isEmpty())
        {
            cancel = true;
            login_email.setError("Enter registered email.");
            focusView = login_email;
            focusView.requestFocus();
        }
        else if(!email.contains("@") || !email.contains("."))
        {
            cancel = true;
            login_email.setError("Enter valid email address.");
            focusView = login_email;
            focusView.requestFocus();
        }


        if(!cancel) {
            progressdialog.show();
            Log.i("Login","Signing In---");
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Log.e("Login", "LoginSuccessfull---");
                    databaseReference.child(StringVariable.USERS).child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds:dataSnapshot.getChildren()) {
                                user_Admindata1.put(ds.getKey(), ds.getValue().toString());
                            }
                            if (!user_Admindata1.get(StringVariable.STUDENTDATA_ADMIN_POST).equals(StringVariable.STUDENTDATA_POST_PI)) {
                                data = user_Admindata1.get(StringVariable.STUDENTDATA_ROLLNO_COLLEGE).split(":sac:");
                                //continue process of extraction

                                progressdialog.setMessage("Getting Data from server...");
                                databaseReference.child(StringVariable.STUDENTDATA_COLLEGE).child(data[1]).child(data[0]).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Log.i("Login", "get User details with roll no : " + data[0] + "+" + data[1]);
                                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                            userdata.put(ds.getKey(), ds.getValue().toString());
                                        }
                                        //continue extraction
                                        continueExtraction();
                                    }


                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        progressdialog.dismiss();
                                        Log.i("Login", "Error in getting user roll data " + databaseError.getMessage());
                                    }
                                });
                            }else {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(StringVariable.STUDENTDATA_ADMIN_STATUS, user_Admindata1.get(StringVariable.STUDENTDATA_ADMIN_STATUS));
                                editor.putString(StringVariable.STUDENTDATA_POST, user_Admindata1.get(StringVariable.STUDENTDATA_POST));
                                editor.putString(StringVariable.STUDENTDATA_ADMIN_NAME,user_Admindata1.get(StringVariable.STUDENTDATA_ADMIN_NAME));
                                editor.putString(StringVariable.STUDENTDATA_ADMIN_POST, user_Admindata1.get(StringVariable.STUDENTDATA_ADMIN_POST));
                                editor.apply();

                                Log.i("Login","shared preference set---");
                                progressdialog.setMessage("Welcome Back...");
                                progressdialog.dismiss();
                                Intent intent =new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            progressdialog.dismiss();
                            Log.i("Login", "Error in getting user data " + databaseError.getMessage());
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressdialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Login Failed\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void continueExtraction() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(StringVariable.STUDENTDATA_NAME,userdata.get(StringVariable.STUDENTDATA_NAME));
        Log.e("NAME",userdata.get(StringVariable.STUDENTDATA_NAME));
        editor.putString(StringVariable.STUDENTDATA_ROLLNO,userdata.get(StringVariable.STUDENTDATA_ROLLNO));
        Log.e("ROLL NO",userdata.get(StringVariable.STUDENTDATA_ROLLNO));
        editor.putString(StringVariable.STUDENTDATA_BRANCH,userdata.get(StringVariable.STUDENTDATA_BRANCH));
        Log.e("BRANCH",userdata.get(StringVariable.STUDENTDATA_BRANCH));
        editor.putString(StringVariable.STUDENTDATA_COLLEGE,userdata.get(StringVariable.STUDENTDATA_COLLEGE));
        Log.e("COLLEGE",userdata.get(StringVariable.STUDENTDATA_COLLEGE));
        editor.putString(StringVariable.STUDENTDATA_YEAR,userdata.get(StringVariable.STUDENTDATA_YEAR));
        Log.e("YEAR",userdata.get(StringVariable.STUDENTDATA_YEAR));
        editor.putString(StringVariable.STUDENTDATA_TCFID,userdata.get(StringVariable.STUDENTDATA_TCFID));
        Log.e("TCF ID",userdata.get(StringVariable.STUDENTDATA_TCFID));
        editor.putString(StringVariable.STUDENTDATA_GENDER,userdata.get(StringVariable.STUDENTDATA_GENDER));
        Log.e("GENDER",userdata.get(StringVariable.STUDENTDATA_GENDER));
        editor.putString(StringVariable.STUDENTDATA_MOBILENO,userdata.get(StringVariable.STUDENTDATA_MOBILENO));
        Log.e("MOBILE NO",userdata.get(StringVariable.STUDENTDATA_MOBILENO));
        editor.putString(StringVariable.STUDENTDATA_EMAILID,userdata.get(StringVariable.STUDENTDATA_EMAILID));
        Log.e("EMAIL ID",userdata.get(StringVariable.STUDENTDATA_EMAILID));
        editor.putString(StringVariable.STUDENTDATA_EVENTS,userdata.get(StringVariable.STUDENTDATA_EVENTS));
        if(userdata.get(StringVariable.REGISTERED)!=null){
            editor.putBoolean(StringVariable.REGISTERED,true);
        }else {
            editor.putBoolean(StringVariable.REGISTERED,false);
        }

        editor.putString(StringVariable.STUDENTDATA_ADMIN_STATUS, user_Admindata1.get(StringVariable.STUDENTDATA_ADMIN_STATUS));
        editor.putString(StringVariable.STUDENTDATA_POST, user_Admindata1.get(StringVariable.STUDENTDATA_POST));
        editor.putString(StringVariable.STUDENTDATA_ADMIN_POST, user_Admindata1.get(StringVariable.STUDENTDATA_ADMIN_POST));
        editor.putString(StringVariable.STUDENTDATA_ADMIN_NAME,user_Admindata1.get(StringVariable.STUDENTDATA_ADMIN_NAME));

        editor.apply();
        Log.i("Login","shared preference set---");
        progressdialog.setMessage("Welcome Back...");
        storeEventsData(userdata.get(StringVariable.STUDENTDATA_EVENTS));
        progressdialog.dismiss();
        Intent intent =new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void storeEventsData(String events) {
        Log.i("Login","storeEventsData");
        if(events!=null) {
            if (!events.isEmpty()) {
                String[] event = events.split(":sac:");
                SharedPreferences pref = getSharedPreferences(StringVariable.SHAREDPREFERENCE_STUDENTEVENTDATA, MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();

                for (int i = 1; i < event.length; i++) {
                    Log.i("Login", "Registerd Event : " + event);
                    editor.putString(event[i], event[i]);
                }
                editor.apply();
                Log.i("Login", "shared preference Events set---");
            }
        }
    }
}
