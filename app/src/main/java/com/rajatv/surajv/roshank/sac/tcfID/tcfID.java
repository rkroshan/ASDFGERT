package com.rajatv.surajv.roshank.sac.tcfID;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajatv.surajv.roshank.sac.MainActivity;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.Signup.SignUp1;
import com.rajatv.surajv.roshank.sac.StringVariable;


public class tcfID extends AppCompatActivity {
    private TextView tcfId;
    //private Button begin;
    private DatabaseReference datarefTCFID,databaseReference;
    SharedPreferences sharedPreferences;
    String tcfidno="";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tcf_id);

        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/typo_style_regular_demo.otf");

        databaseReference = FirebaseDatabase.getInstance().getReference().child(StringVariable.STUDENTDATA_COLLEGE);
        datarefTCFID = FirebaseDatabase.getInstance().getReference().child(StringVariable.STUDENTDATA_TCFID);
        sharedPreferences = getSharedPreferences(StringVariable.SHAREDPREFERENCE_STUDENTDATA,0);
        tcfId = (TextView)findViewById(R.id.tcf_id_textview);
        tcfId.setTypeface(type);
        /*begin = (Button)findViewById(R.id.tcf_id_begin);
        begin.setTypeface(type);*/
        progressDialog = new ProgressDialog(this);

        /*begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(tcfID.this, SignUp1.class));
                finish();
            }
        });*/
        if(sharedPreferences.getBoolean(StringVariable.TCFID_GENERATED,false)){
            tcfId.setText(sharedPreferences.getString(StringVariable.STUDENTDATA_TCFID,""));

        }else {
            generateTcfId();
        }
    }

    private void generateTcfId() {
        progressDialog.setMessage("Generating TCFID...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        Log.i("SignUp2", "Generating TCFID---");
        datarefTCFID.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    tcfidno = dataSnapshot.getValue().toString().split("TCF")[1];
                }catch (Exception e){
                    tcfidno = "";
                }
                Log.i("SignUp2", "getting TCFID : " + tcfId);
                tcfidContinue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.cancel();
                Log.i("SignUp2", "getting TCFID : Fail" + databaseError.getMessage());
            }
        });

    }

    private void tcfidContinue() {

        if (tcfidno.isEmpty()) {
            tcfidno = "TCF" + String.format("%04d", 1);
        } else {
            tcfidno = "TCF" + String.format("%04d", Integer.valueOf(tcfidno) + 1);
        }

        DatabaseReference ref = databaseReference.child(sharedPreferences.getString(StringVariable.STUDENTDATA_COLLEGE,"")).child(sharedPreferences.getString(StringVariable.STUDENTDATA_ROLLNO,""));
        ref.child(StringVariable.STUDENTDATA_TCFID).setValue(tcfidno);

        Log.i("SignUp2", "Setting New TcfID : " + tcfidno);
        datarefTCFID.setValue(tcfidno).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.i("SignUp2", "TCF ID Generated : " + tcfidno);
                    progressDialog.dismiss();
                    tcfId.setText(tcfidno);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(StringVariable.STUDENTDATA_TCFID, tcfidno);
                    editor.putBoolean(StringVariable.TCFID_GENERATED,true);
                    editor.apply();
                    Toast.makeText(getApplicationContext(),"TCF ID GENERATED",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Log.i("SignUp2", "TCF ID Generated (Fail) " + e.getMessage());
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error Occured : Please Try Again", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
