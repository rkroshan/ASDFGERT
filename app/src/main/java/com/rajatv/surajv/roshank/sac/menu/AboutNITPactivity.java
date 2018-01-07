package com.rajatv.surajv.roshank.sac.menu;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.rajatv.surajv.roshank.sac.R;

public class AboutNITPactivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView aboutnitp_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_about_nitpactivity);

        aboutnitp_back = (ImageView) findViewById(R.id.aboutnitp_back);
        aboutnitp_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.aboutnitp_back:
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
