package com.rajatv.surajv.roshank.sac.NitpClubs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.rajatv.surajv.roshank.sac.R;

import java.util.ArrayList;

/**
 * Created by CREATOR on 11/26/2017.
 */

public class NitpClubs extends AppCompatActivity implements View.OnClickListener {

    ImageView nitp_club_back;
    RecyclerView recyclerView;
    ArrayList<NitpClubsObject> mlist = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setupWindowAnimations();
        setContentView(R.layout.nitp_clubs);

        nitp_club_back = (ImageView) findViewById(R.id.nitp_club_back);
        nitp_club_back.setOnClickListener(this);
        recyclerView = (RecyclerView)findViewById(R.id.nitp_clubs_recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        setList();

        NitpClubsAdapter nitpClubsAdapter = new NitpClubsAdapter(mlist);
        recyclerView.setAdapter(nitpClubsAdapter);
    }

    private void setList() {

        mlist.add(new NitpClubsObject("Web and Coding Club",R.mipmap.icon_cm));
        mlist.add(new NitpClubsObject("Literary and Art Club",R.mipmap.logo_literaryclub));
        mlist.add(new NitpClubsObject("Dance Club",R.mipmap.icon_cm));
        mlist.add(new NitpClubsObject("Music Club",R.mipmap.logo_musicclub));
        mlist.add(new NitpClubsObject("Drama and Films Club",R.mipmap.icon_cm));
        mlist.add(new NitpClubsObject("Vista",R.mipmap.logo_photographyclub));
        mlist.add(new NitpClubsObject("Robotics Club",R.mipmap.icon_cm));

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
