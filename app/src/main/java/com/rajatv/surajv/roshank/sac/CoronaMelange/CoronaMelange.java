package com.rajatv.surajv.roshank.sac.CoronaMelange;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Window;
import android.view.WindowManager;


import com.rajatv.surajv.roshank.sac.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CoronaMelange extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<CoronaObject> mlist = new ArrayList<>();
    CoronaMelangeAdapter coronaMelangeAdapter ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_corona_melange);
        setupWindowAnimations();
        recyclerView = (RecyclerView) findViewById(R.id.CoronaMelage_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        //setting mlist
        setList();

        coronaMelangeAdapter = new CoronaMelangeAdapter(mlist);
        recyclerView.setAdapter(coronaMelangeAdapter);
    }

    private void setupWindowAnimations() {
        Slide slide = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            slide = new Slide();
            slide.setDuration(2000);
            getWindow().setEnterTransition(slide);
            getWindow().setExitTransition(slide);
        }
    }

    private void setList() {
        mlist.add(new CoronaObject("Generate Tcf Id",R.mipmap.infowhite));
        mlist.add(new CoronaObject("About Festival",R.mipmap.info_icon));
        mlist.add(new CoronaObject("Technical Event",R.mipmap.tech_event_icon));
        mlist.add(new CoronaObject("Cultural",R.mipmap.cul_event_icon));
        mlist.add(new CoronaObject("Fun Events",R.mipmap.fun_icon));
        mlist.add(new CoronaObject("Workshops",R.mipmap.work_icon));
       // mlist.add(new CoronaObject("SpotLight");
       // mlist.add(new CoronaObject("Schedule");
        mlist.add(new CoronaObject("Sponsors",R.mipmap.sponsorship_icon));
    }
}
