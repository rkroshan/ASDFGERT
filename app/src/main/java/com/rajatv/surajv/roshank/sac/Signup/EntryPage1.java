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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajatv.surajv.roshank.sac.MainActivity;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by CREATOR on 12/10/2017.
 */

public class EntryPage1 extends AppCompatActivity  {

    private static final int RC_SIGN_IN = 101;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    //private SignInButton google_login;
    private Button google_login;
    private GoogleApiClient mGoogleApiClient;
    private DatabaseReference databaseReference;

    ProgressDialog progressDialog;
    private HashMap<String, String> user_Admindata1 = new HashMap<>(),userdata = new HashMap<>();
    private String[] data;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e("EntryPage1---", "Started");
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_entry_1);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(this);



        google_login = (Button)findViewById(R.id.google_signin);
        //google_login = (SignInButton) findViewById(R.id.google_login);
        google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setMessage("Getting accounts...");
                progressDialog.show();

                signIn();
            }
        });

        auth = FirebaseAuth.getInstance();

        /*FirebaseUser user = auth.getCurrentUser();
        if(user != null)
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            Toast.makeText(getApplicationContext(),"Welcome back",Toast.LENGTH_SHORT).show();
        }*/

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this , new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                        progressDialog.dismiss();
                        Toast.makeText(EntryPage1.this, "Conection failed", Toast.LENGTH_SHORT).show();
                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            progressDialog.setMessage("Signing you in...");
            //progressDialog.show(EntryPage1.this, "", "Signing you in...");
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
                mGoogleApiClient.clearDefaultAccountAndReconnect();

            } else {
                // Google Sign In failed, update UI appropriately
                // ...
                progressDialog.dismiss();
                Toast.makeText(EntryPage1.this, " Conection failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("Entry Page", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Entry Page", "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.

                        if (!task.isSuccessful()) {
                            Log.w("Entry Page", "signInWithCredential", task.getException());

                            Toast.makeText(EntryPage1.this, "Something went wrong\n"+task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }else {
                            Toast.makeText(EntryPage1.this, "Successful signin",
                                    Toast.LENGTH_SHORT).show();
                            //start retrieving data if exist
                            gettingdata();
                        }
                    }
                });
    }

    private void gettingdata() {

        DatabaseReference dataref = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS);

        dataref.child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){

                    Toast.makeText(getApplicationContext(),"Welcome back",Toast.LENGTH_SHORT).show();
                    for(DataSnapshot ds:dataSnapshot.getChildren()) {
                        user_Admindata1.put(ds.getKey(), ds.getValue().toString());
                        Log.e("user_Admindata1---",user_Admindata1.size()+"");
                    }
                    data = user_Admindata1.get(StringVariable.STUDENTDATA_ROLLNO_COLLEGE).split(":sac:");
                    //continue process of extraction

                    progressDialog.setMessage("Getting Data from server...");
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
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
                            progressDialog.dismiss();
                            Log.i("Login", "Error in getting user roll data " + databaseError.getMessage());
                        }
                    });
                }else {

                    Toast.makeText(getApplicationContext(),"Welcome To SAC NIT PATNA",Toast.LENGTH_LONG).show();
                    //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
                try {
                    Log.e("Login Database Error", databaseError.getMessage());
                }catch (Exception e){}
            }
        });
    }

    private void continueExtraction() {
        SharedPreferences sharedPreferences = getSharedPreferences(StringVariable.SHAREDPREFERENCE_STUDENTDATA,0);
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
        String tcfid = userdata.get(StringVariable.STUDENTDATA_TCFID);
        if(tcfid==null)
        {
            tcfid="";
            editor.putBoolean(StringVariable.TCFID_GENERATED, false);
        }
        else
            editor.putBoolean(StringVariable.TCFID_GENERATED, true);
        Log.e("TCF ID",tcfid);
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
        Log.e("STUDENTDATA_ADMIN_STA",user_Admindata1.get(StringVariable.STUDENTDATA_ADMIN_STATUS));
        editor.putString(StringVariable.STUDENTDATA_ADMIN_POST, user_Admindata1.get(StringVariable.STUDENTDATA_ADMIN_POST));
        editor.putString(StringVariable.STUDENTDATA_ADMIN_NAME,user_Admindata1.get(StringVariable.STUDENTDATA_ADMIN_NAME));

        editor.apply();
        Log.i("Login","shared preference set---");
        progressDialog.setMessage("Welcome Back...");
        storeEventsData(userdata.get(StringVariable.STUDENTDATA_EVENTS));
        progressDialog.dismiss();
        Intent intent =new Intent(getApplicationContext(), MainActivity.class);
        //startActivity(intent);
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



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    public void onStart() {

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    SharedPreferences mref = getSharedPreferences(StringVariable.SHAREDPREFERENCE_STUDENTDATA, 0);
                    if(mref.getBoolean(StringVariable.REGISTERED, false))
                        Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_SHORT).show();
                }
                // ...
            }
        };
        auth.addAuthStateListener(authStateListener);
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            auth.removeAuthStateListener(authStateListener);
        }
    }


}
