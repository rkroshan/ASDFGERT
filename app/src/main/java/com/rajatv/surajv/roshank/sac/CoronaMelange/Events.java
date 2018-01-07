package com.rajatv.surajv.roshank.sac.CoronaMelange;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.dashboard.RegisterAdapter;
import com.rajatv.surajv.roshank.sac.dashboard.RegisterObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Events extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    Toolbar toolbar_clubs;
    ArrayList<RegisterObject> mlist = new ArrayList<>();
    RegisterAdapter registerAdapter;
    View events_back;

    String data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_events);

        data = getIntent().getStringExtra("Eventdata");
        toolbar_clubs = (Toolbar) findViewById(R.id.toolbar_clubs);
        recyclerView = (RecyclerView) findViewById(R.id.events_recyclerview);
        setBackground(data);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        events_back = (View) findViewById(R.id.events_back);
        events_back.setOnClickListener(this);

        setList(data);

        registerAdapter = new RegisterAdapter(mlist,this);
        recyclerView.setAdapter(registerAdapter);
    }

    private void setBackground(String data) {
        if(data!=null) {
            Log.e("clubname---",data);
            switch (data) {
                case "CONCREATE":
                    toolbar_clubs.setBackgroundResource(R.mipmap.conu);
                    recyclerView.setBackgroundResource(R.mipmap.conl);
                    break;
                case "BYTE WORLD":
                    toolbar_clubs.setBackgroundResource(R.mipmap.byu);
                    recyclerView.setBackgroundResource(R.mipmap.byl);
                    break;
                case "ROBOTICS":
                    toolbar_clubs.setBackgroundResource(R.mipmap.robou);
                    recyclerView.setBackgroundResource(R.mipmap.robol);
                    break;
                case "OHM":
                    toolbar_clubs.setBackgroundResource(R.mipmap.ohmu);
                    recyclerView.setBackgroundResource(R.mipmap.ohml);
                    break;
                case "AAYAM":
                    toolbar_clubs.setBackgroundResource(R.mipmap.aayamu);
                    recyclerView.setBackgroundResource(R.mipmap.aayaml);
                    break;
                case "YANTRIKI":
                    toolbar_clubs.setBackgroundResource(R.mipmap.yantrikiu);
                    recyclerView.setBackgroundResource(R.mipmap.yantrikil);
                    break;
                case "ABHINAY":
                   toolbar_clubs.setBackgroundResource(R.mipmap.abhinayu);
                    recyclerView.setBackgroundResource(R.mipmap.abhinayl);
                    break;
                case "AVLOKAN":
                    toolbar_clubs.setBackgroundResource(R.mipmap.avlu);
                    recyclerView.setBackgroundResource(R.mipmap.avll);
                    break;
                case "NRITYANGANA":
                    toolbar_clubs.setBackgroundResource(R.mipmap.nirtu);
                    recyclerView.setBackgroundResource(R.mipmap.nirtl);
                    break;
                case "KALAKRITI":
                    toolbar_clubs.setBackgroundResource(R.mipmap.kalau);
                    recyclerView.setBackgroundResource(R.mipmap.kalal);
                    break;
                case "SANHITA":
                    toolbar_clubs.setBackgroundResource(R.mipmap.sanhitau);
                    recyclerView.setBackgroundResource(R.mipmap.sanhital);
                    break;
                case "RAAGA":
                    toolbar_clubs.setBackgroundResource(R.mipmap.raagau);
                    recyclerView.setBackgroundResource(R.mipmap.raagal);
                    break;
                case "PRATIBIMB":
                    toolbar_clubs.setBackgroundResource(R.mipmap.prau);
                    recyclerView.setBackgroundResource(R.mipmap.pral);
                    break;
            }
        }
    }

    private void setList(String data) {
        if(data!=null) {
            switch (data) {
                case "CONCREATE":
                    mlist.add(new RegisterObject("C","Cryptic","Concrete"));
                    mlist.add(new RegisterObject("R", "Real Estate", "Concrete"));
                    mlist.add(new RegisterObject("B", "Bridge Designing", "Concrete"));
                    mlist.add(new RegisterObject("E", "Estratto", "Concrete"));
                    mlist.add(new RegisterObject("N","Novus","Concrete"));
                    break;
                case "BYTE WORLD":
                    mlist.add(new RegisterObject("A", "Algo-Z-Ripper", "Byteworld"));
                    mlist.add(new RegisterObject("W", "Web Weaver", "Byteworld"));
                    mlist.add(new RegisterObject("P", "Panorama", "Byteworld"));
                    mlist.add(new RegisterObject("H","Hackerzilla", "Byteworld"));
                    mlist.add(new RegisterObject("A", "Appathon", "Byteworld"));
                    mlist.add(new RegisterObject("L", "Logo Design", "Byteworld"));
                    break;
                case "ROBOTICS":
                    mlist.add(new RegisterObject("P", "Path Seguidor", "Robotics"));
                    mlist.add(new RegisterObject("D", "Death Race", "Robotics"));
                    mlist.add(new RegisterObject("R", "Robo Soccer", "Robotics"));
                    mlist.add(new RegisterObject("L", "Lift Off", "Robotics"));
                    break;
                case "OHM":
                    mlist.add(new RegisterObject("V", "virtual circuitarix", "Ohm"));
                    mlist.add(new RegisterObject("S", "Smash debug", "Ohm"));
                    mlist.add(new RegisterObject("E", "Electromaze", "Ohm"));
                    mlist.add(new RegisterObject("O", "Open hardware", "Ohm"));
                    break;
                case "AAYAM":
                    mlist.add(new RegisterObject("J", "Jenga", "Aayam"));
                    mlist.add(new RegisterObject("A", "Ayame (Main Design)", "Aayam"));
                    mlist.add(new RegisterObject("H", "Hatsumi (Model Making)", "Aayam"));
                    mlist.add(new RegisterObject("J", "Junihitoe (Apparel Making)", "Aayam"));
                    mlist.add(new RegisterObject("T", "Tsukimi (Art Expo)", "Aayam"));
                    mlist.add(new RegisterObject("J","Jinsei (Photography)", "Aayam"));
                    break;
                case "YANTRIKI":
                    mlist.add(new RegisterObject("M", "Machinist", "Yantriki"));
                    mlist.add(new RegisterObject("M", "Mech-a-rush","Yantriki"));
                    mlist.add(new RegisterObject("T", "Trivia do moto","Yantriki"));
                    mlist.add(new RegisterObject("I", "Innovative Examplar", "Yantriki"));
                    break;
                case "ABHINAY":
                    mlist.add(new RegisterObject("A", "Andaaz e adakari", "Abhinay"));
                    mlist.add(new RegisterObject("P", "Poezia", "Ahhinay"));
                    mlist.add(new RegisterObject("S", "Skit/Play", "Abhinay"));
                    mlist.add(new RegisterObject("F", "Film-ignito","Abhinay"));
                    mlist.add(new RegisterObject("C", "Cine Quiz","Abhinay"));
                    break;
                case "AVLOKAN":
                    mlist.add(new RegisterObject("G", "Geek-o-mania", "Avlokan"));
                    mlist.add(new RegisterObject("V", "Virtual placement", "Avlokan"));
                    mlist.add(new RegisterObject("M", "Mystery solving", "Avlokan"));
                    mlist.add(new RegisterObject("W", "Word rush", "Avlokan"));
                    mlist.add(new RegisterObject("C", "Clash-o-mania", "Avlokan"));
                    break;
                case "NRITYANGANA":
                    mlist.add(new RegisterObject("S", "Solo Dance", "Nrityangana"));
                    mlist.add(new RegisterObject("D", "Duet / Couple Dance", "Nrityangana"));
                    mlist.add(new RegisterObject("G", "Group Dance", "Nrityangana"));
                    mlist.add(new RegisterObject("I", "Impromptu", "Nrityangana"));
                    break;
                case "KALAKRITI":
                    mlist.add(new RegisterObject("O", "Origami", "Kalakriti"));
                    mlist.add(new RegisterObject("F", "Face Canvas","Kalakriti"));
                    mlist.add(new RegisterObject("E", "Express your mood", "Kalakriti"));
                    mlist.add(new RegisterObject("B","Build your shape","Kalakriti"));
                    mlist.add(new RegisterObject("N","No brushes","Kalakriti"));
                    mlist.add(new RegisterObject("T", "Tooncon", "Kalakriti"));
                    mlist.add(new RegisterObject("S","Salty feeling", "Kalakriti"));
                    mlist.add(new RegisterObject("M", "Make your own T", "Kalakriti"));
                    mlist.add(new RegisterObject("R", "Rang barse", "Kalakriti"));
                    mlist.add(new RegisterObject("G", "Get inked","Kalakriti"));
                    break;
                case "SANHITA":
                    mlist.add(new RegisterObject("P","Parliamentary Debate", "Sanhita"));
                    mlist.add(new RegisterObject("D", "Debate", "Sanhita"));
                    mlist.add(new RegisterObject("M", "A Minute to Win it","Sanhita"));
                    mlist.add(new RegisterObject("P", "Pic a Story","Sanhita"));
                    mlist.add(new RegisterObject("S","Six Word Story", "Sanhita"));
                    mlist.add(new RegisterObject("O", "Open Mic","Sanhita"));
                    break;
                case "RAAGA":
                    mlist.add(new RegisterObject("S","Solo Singing","Raaga"));
                    mlist.add(new RegisterObject("M","Music Mob","Raaga"));
                    mlist.add(new RegisterObject("A", "Antakshari","Raaga"));
                    break;
                case "PRATIBIMB":
                    mlist.add(new RegisterObject("B","Brain Stroke(compulsary round)","Pratibimb"));
                    mlist.add(new RegisterObject("E", "Exhiber(Talent Round)", "Pratibimb"));
                    mlist.add(new RegisterObject("R","Round-De NIT Patna(Treasure Hunt)","Pratibimb"));
                    mlist.add(new RegisterObject("P", "Personal Interview", "Pratibimb"));
                    mlist.add(new RegisterObject("P","Photoshoot","Pratibimb"));
                    mlist.add(new RegisterObject("G","Grand Finale", "Pratibimb"));
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.events_back:
                finish();
                break;
        }
    }
}
