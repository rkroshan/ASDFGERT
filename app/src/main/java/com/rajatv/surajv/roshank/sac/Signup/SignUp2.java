package com.rajatv.surajv.roshank.sac.Signup;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;

public class SignUp2 extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SignUp2";
    private String name,college,rollno,branch,gender,mobileno,year,email,password,getemail;
    EditText signup2_email,signup2_password;
    Button btn_register;
    String[] realemail;
    String tcfId="";
    ProgressDialog progressdialog;

    View focusView;


    private SharedPreferences sharedPreferences;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReferenceuseremail,databaseReference,datarefTCFID;
    private int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("SignUp2---","Started");
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up2);

        init();
    }

    private void init() {
        signup2_email = (EditText) findViewById(R.id.signup2_email);
        signup2_password = (EditText) findViewById(R.id.signup2_password);
        btn_register = (Button)findViewById(R.id.btn_register);

        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/widolte-demo.regular-demo.otf");
        btn_register.setTypeface(type);

        sharedPreferences = getSharedPreferences(StringVariable.SHAREDPREFERENCE_STUDENTDATA,MODE_PRIVATE);

        Bundle args = getIntent().getExtras();
        name = args.getString(StringVariable.STUDENTDATA_NAME);
        college = args.getString(StringVariable.STUDENTDATA_COLLEGE);
        rollno = args.getString(StringVariable.STUDENTDATA_ROLLNO);
        branch = args.getString(StringVariable.STUDENTDATA_BRANCH);
        gender = args.getString(StringVariable.STUDENTDATA_GENDER);
        mobileno = args.getString(StringVariable.STUDENTDATA_MOBILENO);
        year = args.getString(StringVariable.STUDENTDATA_YEAR);

        btn_register.setOnClickListener(this);

        //set auth
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(StringVariable.STUDENTDATA_COLLEGE);
        databaseReferenceuseremail = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS);
        datarefTCFID = FirebaseDatabase.getInstance().getReference().child(StringVariable.STUDENTDATA_TCFID);


    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_register:
                checkdata();
                break;
        }
    }

    private void checkdata() {
        email = signup2_email.getText().toString();
        password = signup2_password.getText().toString();
       // realemail =email.split("@");

        boolean cancel = false;

        if(password.length()<8)
        {
            cancel = true;
            signup2_password.setError("Password length must be at least 8");
            focusView = signup2_password;
            focusView.requestFocus();
        }
        if(email.isEmpty())
        {
            cancel = true;
            signup2_email.setError("Enter registered email.");
            focusView = signup2_email;
            focusView.requestFocus();
        }
        else if(!email.contains("@") || !email.contains("."))
        {
            cancel = true;
            signup2_email.setError("Enter valid email address");
            focusView = signup2_email;
            focusView.requestFocus();

        }/*
        else if(realemail[0].contains(".")||realemail[0].contains("$")||realemail[0].contains("#")
                ||realemail[0].contains("[")||realemail[0].contains("]"))
        {
            cancel = true;
            signup2_email.setError("Email Id should not contain '.', '$', '#', '[', ']' before @ symbol");
            focusView = signup2_email;
            focusView.requestFocus();
        }*/
        if(!cancel)
        {
            ConfirmationAlertBox();
        }
    }

    private void ConfirmationAlertBox() {
        getemail = signup2_email.getText().toString();
        @SuppressLint("RestrictedApi") final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(SignUp2.this,R.style.myDialog));
        builder.setTitle("Attention ");
        builder.setMessage("Please Check Your Details\n"+
        "Name : "+name+"\nRollno : "+rollno+"\nBranch : "+branch+"\nCollege : "+college+"\nGender : "+gender+"\nMobile No : "+mobileno+
        "\nEmail id : "+getemail)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       AuthenticationStarts();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        builder.show();
    }

    private void AuthenticationStarts() {
        progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Registering...");
        progressdialog.setCancelable(false);
        progressdialog.setCanceledOnTouchOutside(false);
        progressdialog.show();
        createUser();
    }

    private void createUser(){
        Log.i("SignUp2","Starting Creating User...");
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if(task.isSuccessful()){
                            Log.i("SignUp2","Auth_Success-- "+task.getResult().toString());
                            Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_SHORT).show();
                            progressdialog.setMessage("Creating Your Account...");
                            checkRollExist();

                        }
                    }

                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressdialog.dismiss();
                Log.i("SignUp2","Auth_Failed-- "+e.getMessage());
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void settingUpDetails() {
        Log.i("SignUp2","SettingUpDeatils the details");
        DatabaseReference ref = databaseReference.child(college).child(rollno);
        databaseReferenceuseremail.child(mAuth.getCurrentUser().getUid()).child(StringVariable.STUDENTDATA_ROLLNO_COLLEGE).setValue(rollno + ":sac:" + college);
        databaseReferenceuseremail.child(mAuth.getCurrentUser().getUid()).child(StringVariable.STUDENTDATA_ADMIN_STATUS).setValue(StringVariable.STUDENTDATA_ADMIN_STATUS_TRUE);
        databaseReferenceuseremail.child(mAuth.getCurrentUser().getUid()).child(StringVariable.STUDENTDATA_ADMIN_POST).setValue("0");
        databaseReferenceuseremail.child(mAuth.getCurrentUser().getUid()).child(StringVariable.STUDENTDATA_ADMIN_NAME).setValue(name);
        ref.child(StringVariable.STUDENTDATA_NAME).setValue(name);
        ref.child(StringVariable.STUDENTDATA_ROLLNO).setValue(rollno);
        ref.child(StringVariable.STUDENTDATA_BRANCH).setValue(branch);
        ref.child(StringVariable.STUDENTDATA_COLLEGE).setValue(college);
        ref.child(StringVariable.STUDENTDATA_YEAR).setValue(year);
        ref.child(StringVariable.STUDENTDATA_GENDER).setValue(gender);
        ref.child(StringVariable.STUDENTDATA_MOBILENO).setValue(mobileno);
        ref.child(StringVariable.STUDENTDATA_EMAILID).setValue(getemail);
        ref.child(StringVariable.STUDENTDATA_EVENTS).setValue("").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.i("SignUp2","Data Upload-- "+task.getResult());
                    Toast.makeText(getApplicationContext(), "Data stored", Toast.LENGTH_SHORT).show();
                    progressdialog.setMessage("Generating TCF ID...");
                    generateTcfId();
                }else {
                    progressdialog.dismiss();
                    Log.i("SignUp2","Data UploadFail-- "+task.getResult());
                }
            }
        });
    }

    private void checkRollExist(){
        Log.i("SignUp2","checkRollExist---");
        Log.i("Signup2",databaseReference.toString());

        databaseReference.child(college).child(rollno).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("Signup2",databaseReference.child(college).toString());
                Log.i("SignUp2","onDataChange---");
                try {
                    Log.i("SignUp2", dataSnapshot.getValue().toString());
                    if (dataSnapshot.getValue().toString() != null) {
                        progressdialog.dismiss();
                        Log.i("SignUp2", "Roll Exist :" + rollno);
                        Toast.makeText(getApplicationContext(), "This RollNo Already Exists", Toast.LENGTH_SHORT).show();
                        flag = 1;
                    }else{
                        settingUpDetails();
                    }
                }catch (NullPointerException n){
                    Log.e("SignUp",dataSnapshot.toString());
                    settingUpDetails();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressdialog.dismiss();
                Log.i("SignUp2","checkRollNo Exist Error :"+databaseError.getMessage());
            }
        });


    }

    private void generateTcfId() {
        Log.i("SignUp2", "Generating TCFID---");
        datarefTCFID.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    tcfId = dataSnapshot.getValue().toString().split("TCF")[1];
                }catch (Exception e){
                    tcfId = "";
                }
                Log.i("SignUp2", "getting TCFID : " + tcfId);
                tcfidContinue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressdialog.cancel();
                Log.i("SignUp2", "getting TCFID : Fail" + databaseError.getMessage());
            }
        });

    }
    private void tcfidContinue(){

        if (tcfId.isEmpty()) {
            tcfId = "TCF" + String.format("%04d", 1);
        } else {
            tcfId = "TCF" + String.format("%04d", Integer.valueOf(tcfId) + 1);
        }

        DatabaseReference ref = databaseReference.child(college).child(rollno);
        ref.child(StringVariable.STUDENTDATA_TCFID).setValue(tcfId);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(StringVariable.STUDENTDATA_NAME, name);
        editor.putString(StringVariable.STUDENTDATA_ROLLNO, rollno);
        editor.putString(StringVariable.STUDENTDATA_BRANCH, branch);
        editor.putString(StringVariable.STUDENTDATA_TCFID, tcfId);
        editor.putString(StringVariable.STUDENTDATA_COLLEGE, college);
        editor.putString(StringVariable.STUDENTDATA_GENDER, gender);
        editor.putString(StringVariable.STUDENTDATA_MOBILENO, mobileno);
        editor.putString(StringVariable.STUDENTDATA_EMAILID, getemail);
        editor.putString(StringVariable.STUDENTDATA_YEAR, year);
        editor.putString(StringVariable.STUDENTDATA_ADMIN_STATUS, StringVariable.STUDENTDATA_ADMIN_STATUS_FALSE);
        editor.putString(StringVariable.STUDENTDATA_ADMIN_POST, "0");
        editor.putString(StringVariable.STUDENTDATA_ADMIN_NAME, name);
        editor.putString(StringVariable.STUDENTDATA_EVENTS, "");
        editor.apply();

        Log.i("SignUp2","Setting New TcfID : "+tcfId);
        datarefTCFID.setValue(tcfId).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.i("SignUp2","TCF ID Generated : "+tcfId);
                    progressdialog.dismiss();
                    Intent intent = new Intent(getApplicationContext(), com.rajatv.surajv.roshank.sac.tcfID.tcfID.class);
                    intent.putExtra(StringVariable.STUDENTDATA_TCFID,tcfId);
                    startActivity(intent);
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressdialog.dismiss();
                Log.i("SignUp2","TCF ID Generated (Fail) "+e.getMessage());
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error Occured : Please Try Again", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
