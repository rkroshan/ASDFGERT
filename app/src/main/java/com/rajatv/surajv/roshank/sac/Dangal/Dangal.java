package com.rajatv.surajv.roshank.sac.Dangal;


import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
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
public class Dangal extends AppCompatActivity implements View.OnClickListener {


    ImageView dangal_back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setupWindowAnimations();
        setContentView(R.layout.fragment_dangal);

        dangal_back = (ImageView) findViewById(R.id.dangal_back);
        dangal_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        finish();
    }

    private void setupWindowAnimations() {
        Fade fade = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            fade = new Fade();
            fade.setDuration(1000);
            Log.e("Dangal","AnimationStarts");
            getWindow().setEnterTransition(fade);
            //Log.e("Dangal","Animationends");
            //getWindow().setExitTransition(fade);
        }
    }
}
