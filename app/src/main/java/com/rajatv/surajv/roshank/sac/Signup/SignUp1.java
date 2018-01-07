package com.rajatv.surajv.roshank.sac.Signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajatv.surajv.roshank.sac.MainActivity;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.rajatv.surajv.roshank.sac.dashboard.Dashboard;
import com.rajatv.surajv.roshank.sac.dashboard.Profile;

import java.util.ArrayList;


public class SignUp1 extends AppCompatActivity implements View.OnClickListener {

    private EditText signup1_name, signup1_college, signup1_roll, signup1_branch, signup1_mobile, signup1_year;
    private RadioButton rb_male, rb_female;
    private Spinner signup1_clg_spinner;
    private Button signup1_btn_next;
    private TextView gender_error;
    private String name, college, rollno, branch, mobileno, gender, year,getemail;
    private ProgressDialog progressDialog;

    View focusView;

    ArrayList<String> mlist = new ArrayList<>();
    ArrayAdapter<String> arrayadapter;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference, databaseReferenceuseremail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("SignUp1---", "Started");
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up1);

        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(StringVariable.STUDENTDATA_COLLEGE);
        databaseReferenceuseremail = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS);
        signup1_name = (EditText) findViewById(R.id.signup1_name);
        signup1_college = (EditText) findViewById(R.id.signup1_college);
        signup1_roll = (EditText) findViewById(R.id.signup1_roll);
        signup1_branch = (EditText) findViewById(R.id.signup1_branch);
        signup1_mobile = (EditText) findViewById(R.id.signup1_mobile);
        signup1_year = (EditText) findViewById(R.id.signup1_year);
        progressDialog = new ProgressDialog(this);

        rb_male = (RadioButton) findViewById(R.id.rb_male);
        rb_female = (RadioButton) findViewById(R.id.rb_female);


        gender_error = (TextView) findViewById(R.id.gender_error);


        signup1_clg_spinner = (Spinner) findViewById(R.id.signup1_clg_spinner);
        signup1_btn_next = (Button) findViewById(R.id.signup1_btn_next);

        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/widolte-demo.regular-demo.otf");
        signup1_btn_next.setTypeface(type);

        signup1_btn_next.setOnClickListener(this);

        setList();
        arrayadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mlist);
        arrayadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        signup1_clg_spinner.setAdapter(arrayadapter);

        signup1_clg_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                ((TextView)adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.white));
                switch (i) {
                    case 0:
                        signup1_college.setVisibility(View.GONE);
                        college = signup1_clg_spinner.getSelectedItem().toString();
                        break;
                    case 1:
                        signup1_college.setText("NIT Patna");
                        signup1_college.setVisibility(View.VISIBLE);
                        signup1_college.setEnabled(false);
                        break;
                    case 2:
                        signup1_college.setText("");
                        signup1_college.setHint("COLLEGE");
                        signup1_college.setEnabled(true);
                        signup1_college.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    private void setList() {
        mlist.add("Select College");
        mlist.add("NIT Patna");
        mlist.add("Others");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup1_btn_next:
                checkdata();
                break;
        }
    }

    private void checkdata() {
        name = signup1_name.getText().toString();
        college = signup1_clg_spinner.getSelectedItem().toString();

        rollno = signup1_roll.getText().toString();
        branch = signup1_branch.getText().toString();
        mobileno = signup1_mobile.getText().toString();
        year = signup1_year.getText().toString();
        boolean cancel = false;

        if (mobileno.length() != 10) {
            cancel = true;
            signup1_mobile.setError("Enter 10 digit mobile number");
            focusView = signup1_mobile;
            focusView.requestFocus();
        } else {
            int mob = (int) mobileno.charAt(0);
            if (mob < 55) {
                cancel = true;
                signup1_mobile.setError("Invalid mobile number");
                focusView = signup1_mobile;
                focusView.requestFocus();
            }
        }

        if (year.isEmpty() || year.length()>1) {
            cancel = true;
            signup1_year.setError("Enter 1, 2, 3, 4 or 5");
            focusView = signup1_year;
            focusView.requestFocus();
        } else {
             int year1 = (int)year.charAt(0);
            if (year1<49  || year1 > 53) {
                cancel = true;
                signup1_year.setError("Enter 1, 2, 3, 4 or 5");
                focusView = signup1_year;
                focusView.requestFocus();
            }
        }
        if (branch.isEmpty()) {
            cancel = true;
            signup1_branch.setError("Enter branch");
            focusView = signup1_branch;
            focusView.requestFocus();
        }
        if (rollno.isEmpty()) {
            cancel = true;
            signup1_roll.setError("Enter roll number");
            focusView = signup1_roll;
            focusView.requestFocus();
        }
        if (name.isEmpty()) {
            cancel = true;
            signup1_name.setError("Enter name");
            focusView = signup1_name;
            focusView.requestFocus();
        }  else if (college.equals(mlist.get(0))) {
            cancel = true;
            college="";
            gender_error.setText("*Required College");
            signup1_college.setBackgroundResource(R.drawable.edittext_bg_required);
            gender_error.setVisibility(View.VISIBLE);
        } else if (college.equals(mlist.get(2))) {
            college = signup1_college.getText().toString();
            if(college.isEmpty())
            {
                cancel = true;
                signup1_college.setError("Enter college name");
                focusView = signup1_name;
                focusView.requestFocus();
            }
        }else if (!(rb_male.isChecked() || rb_female.isChecked())) {
            cancel = true;
            gender_error.setText("*Requied Gender");
            gender_error.setVisibility(View.VISIBLE);
        }else {
            gender_error.setVisibility(View.GONE);
        }

        if (!cancel) {
            if (rb_male.isChecked()) {
                gender = "male";
            } else {
                gender = "female";
            }
            /*Intent intent = new Intent(this, SignUp1.class);
            Bundle args = new Bundle();
            args.putString(StringVariable.STUDENTDATA_NAME, name);
            args.putString(StringVariable.STUDENTDATA_COLLEGE, college);
            args.putString(StringVariable.STUDENTDATA_ROLLNO, rollno);
            args.putString(StringVariable.STUDENTDATA_BRANCH, branch);
            args.putString(StringVariable.STUDENTDATA_MOBILENO, mobileno);
            args.putString(StringVariable.STUDENTDATA_GENDER, gender);
            args.putString(StringVariable.STUDENTDATA_YEAR, year);
            intent.putExtras(args);
            startActivity(intent);
            *///finish();

            BeginRegistration();
        }
    }

    private void BeginRegistration() {
        checkRollExist();
    }

    private void checkRollExist() {
        progressDialog.setMessage("Checking Roll No...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        Log.i("SignUp1", "checkRollExist---");
        Log.i("Signup2", databaseReference.toString());

        databaseReference.child(college).child(rollno).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("Signup2", databaseReference.child(college).toString());
                Log.i("SignUp1", "onDataChange---");
                try {
                    Log.i("SignUp1", dataSnapshot.getValue().toString());
                    if (dataSnapshot.getValue().toString() != null) {
                        progressDialog.dismiss();
                        Log.i("SignUp1", "Roll Exist :" + rollno);
                        Toast.makeText(getApplicationContext(), "This RollNo Already Exists", Toast.LENGTH_SHORT).show();
                    } else {
                        settingUpDetails();
                    }
                } catch (NullPointerException n) {
                    Log.e("SignUp", dataSnapshot.toString());
                    settingUpDetails();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
                Log.i("SignUp1", "checkRollNo Exist Error :" + databaseError.getMessage());
            }
        });


    }

    private void settingUpDetails() {
        getemail = mAuth.getCurrentUser().getEmail().toString();
        Log.i("SignUp1","SettingUpDeatils the details");
        DatabaseReference ref = databaseReference.child(college).child(rollno);
        databaseReferenceuseremail.child(mAuth.getCurrentUser().getUid()).child(StringVariable.STUDENTDATA_ROLLNO_COLLEGE).setValue(rollno + ":sac:" + college);
        databaseReferenceuseremail.child(mAuth.getCurrentUser().getUid()).child(StringVariable.STUDENTDATA_ADMIN_STATUS).setValue(StringVariable.STUDENTDATA_ADMIN_STATUS_FALSE);
        databaseReferenceuseremail.child(mAuth.getCurrentUser().getUid()).child(StringVariable.STUDENTDATA_ADMIN_POST).setValue("NA");
        databaseReferenceuseremail.child(mAuth.getCurrentUser().getUid()).child(StringVariable.STUDENTDATA_ADMIN_NAME).setValue(name);
        databaseReferenceuseremail.child(mAuth.getCurrentUser().getUid()).child(StringVariable.STUDENTDATA_EMAILID).setValue(mAuth.getCurrentUser().getEmail().toString());
        ref.child(StringVariable.STUDENTDATA_NAME).setValue(name);
        ref.child(StringVariable.STUDENTDATA_ROLLNO).setValue(rollno);
        ref.child(StringVariable.STUDENTDATA_BRANCH).setValue(branch);
        ref.child(StringVariable.STUDENTDATA_COLLEGE).setValue(college);
        ref.child(StringVariable.STUDENTDATA_YEAR).setValue(year);
        ref.child(StringVariable.STUDENTDATA_GENDER).setValue(gender);
        ref.child(StringVariable.STUDENTDATA_MOBILENO).setValue(mobileno);
        ref.child(StringVariable.STUDENTDATA_EMAILID).setValue(getemail);
        ref.child(StringVariable.REGISTERED).setValue("1");
        ref.child(StringVariable.STUDENTDATA_EVENTS).setValue("").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.i("SignUp1","Data Upload-- "+task.getResult());
                    Toast.makeText(getApplicationContext(), "Data stored", Toast.LENGTH_SHORT).show();
                    progressDialog.setMessage("Saving Up Details...");
                    SavingDetails();
                }else {
                    progressDialog.dismiss();
                    Log.i("SignUp1","Data UploadFail-- "+task.getResult());
                }
            }
        });
    }

    private void SavingDetails() {
        SharedPreferences sharedPreferences = getSharedPreferences(StringVariable.SHAREDPREFERENCE_STUDENTDATA,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(StringVariable.STUDENTDATA_NAME, name);
        editor.putString(StringVariable.STUDENTDATA_ROLLNO, rollno);
        editor.putString(StringVariable.STUDENTDATA_BRANCH, branch);
        editor.putString(StringVariable.STUDENTDATA_COLLEGE, college);
        editor.putString(StringVariable.STUDENTDATA_GENDER, gender);
        editor.putString(StringVariable.STUDENTDATA_MOBILENO, mobileno);
        editor.putString(StringVariable.STUDENTDATA_EMAILID, getemail);
        editor.putString(StringVariable.STUDENTDATA_YEAR, year);
        editor.putString(StringVariable.STUDENTDATA_ADMIN_STATUS, StringVariable.STUDENTDATA_ADMIN_STATUS_FALSE);
        editor.putString(StringVariable.STUDENTDATA_ADMIN_POST, "0");
        editor.putString(StringVariable.STUDENTDATA_ADMIN_NAME, name);
        editor.putString(StringVariable.STUDENTDATA_EVENTS, "");
        editor.putBoolean(StringVariable.REGISTERED,true);
        editor.apply();
        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(),"Registration Successful",Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(), Dashboard.class));
        finish();
    }
}
