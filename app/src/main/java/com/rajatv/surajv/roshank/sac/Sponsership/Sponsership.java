package com.rajatv.surajv.roshank.sac.Sponsership;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.rajatv.surajv.roshank.sac.MainActivity;
import com.rajatv.surajv.roshank.sac.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Sponsership extends AppCompatActivity implements View.OnClickListener {

    ImageView sponser_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fragment_sponsership);

        sponser_back = (ImageView) findViewById(R.id.sponser_back);
        sponser_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
