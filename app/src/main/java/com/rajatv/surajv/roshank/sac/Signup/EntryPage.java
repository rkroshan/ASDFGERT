package com.rajatv.surajv.roshank.sac.Signup;

import android.content.Intent;
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

import com.google.firebase.auth.FirebaseAuth;
import com.rajatv.surajv.roshank.sac.MainActivity;
import com.rajatv.surajv.roshank.sac.R;

/**
 * Created by CREATOR on 11/4/2017.
 */

public class EntryPage extends AppCompatActivity implements View.OnClickListener {

    private Button entry_login,entry_signup;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e("EntryPage---","Started");
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_entry);

        entry_login = (Button) findViewById(R.id.entry_login);
        entry_signup = (Button) findViewById(R.id.entry_signup);

        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/widolte-demo.regular-demo.otf");
        entry_login.setTypeface(type);
        entry_signup.setTypeface(type);

        entry_signup.setOnClickListener(this);
        entry_login.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(auth.getCurrentUser()!=null){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            auth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.entry_login:
                startActivity(new Intent(this,Login.class));
                finish();
                break;
            case R.id.entry_signup:
                startActivity(new Intent(this,SignUp1.class));
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
