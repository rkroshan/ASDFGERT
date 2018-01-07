package com.rajatv.surajv.roshank.sac.CoronaMelange;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rajatv.surajv.roshank.sac.MainActivity;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.rajatv.surajv.roshank.sac.dashboard.Registered;
import com.rajatv.surajv.roshank.sac.menu.AboutFestival;
import com.rajatv.surajv.roshank.sac.tcfID.tcfID;

import java.util.ArrayList;

import static com.rajatv.surajv.roshank.sac.R.id.corona_upper;
import static com.rajatv.surajv.roshank.sac.R.layout.fragment_clubs;
import static java.security.AccessController.getContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class Clubs extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    ArrayList<String> mlist = new ArrayList<>();
    ClubsAdapter clubsAdapter;
    Toolbar toolbar_clubs;
    View clubs_back;
    String field;
    SharedPreferences sharedPreferences;
    Boolean registered;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_clubs);

        field = getIntent().getStringExtra("field");

        toolbar_clubs = (Toolbar) findViewById(R.id.toolbar_clubs);
        clubs_back = (View) findViewById(R.id.clubs_back);
        clubs_back.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.clubs_RecyclerView);
        gridLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        sharedPreferences = getSharedPreferences(StringVariable.SHAREDPREFERENCE_STUDENTDATA,0);
        registered = sharedPreferences.getBoolean(StringVariable.REGISTERED,false);

        //set mlist 009846
        setList();

        clubsAdapter = new ClubsAdapter(mlist,this);
        recyclerView.setAdapter(clubsAdapter);
    }

    private void setList() {
        switch (field){
            /*case "Generate Tcf Id":
                if(registered) {
                    startActivity(new Intent(this, tcfID.class));
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"Please Register Yourself first",Toast.LENGTH_LONG).show();
                }
                break;*/
            case "About Festival":
                startActivity(new Intent(this,AboutFestival.class));
                finish();
                break;
            case "Technical Event":
                mlist.add("CONCREATE");
                mlist.add("BYTE WORLD");
                mlist.add("ROBOTICS");
                mlist.add("OHM");
                mlist.add("AAYAM");
                mlist.add("YANTRIKI");
                break;
            case "Cultural":
                toolbar_clubs.setBackgroundResource(R.mipmap.culturaluu);
                recyclerView.setBackgroundResource(R.mipmap.culturalu);
                mlist.add("PRATIBIMB");
                mlist.add("RAAGA");
                mlist.add("SANHITA");
                mlist.add("AVLOKAN");
                mlist.add("NRITYANGANA");
                mlist.add("KALAKRITI");
                mlist.add("ABHINAY");
                break;
            case "Fun Events":
                toolbar_clubs.setBackgroundResource(R.mipmap.funu);
                recyclerView.setBackgroundResource(R.mipmap.funl);
                mlist.add("INFERNO X");
                mlist.add("GULLY CRICKET");
                mlist.add("FUTSAL");
                mlist.add("PAPER DANCE");
                mlist.add("VIRTUAL JUNKIE");
                mlist.add("STREETS");
                break;
            case "Workshops":
                toolbar_clubs.setBackgroundResource(R.mipmap.workshopu);
                recyclerView.setBackgroundResource(R.mipmap.workshopl);
                break;


        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.clubs_back:
                finish();
        }
    }
}
