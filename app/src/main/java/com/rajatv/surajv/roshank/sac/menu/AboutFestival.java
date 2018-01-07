package com.rajatv.surajv.roshank.sac.menu;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rajatv.surajv.roshank.sac.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFestival extends AppCompatActivity implements View.OnClickListener {

    private ImageView aboutfestival_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_about_nitp);

        aboutfestival_back = (ImageView) findViewById(R.id.aboutfestival_back);
        aboutfestival_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.aboutfestival_back:
                try{
                    Intent
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("www.nitp.ac.in"));
                    startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(this,"Could not find the Link.",Toast.LENGTH_SHORT).show();}
                break;
        }
    }
}
