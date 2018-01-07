package com.rajatv.surajv.roshank.sac.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rajatv.surajv.roshank.sac.CoronaMelange.CoronaMelange;
import com.rajatv.surajv.roshank.sac.MainActivity;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.dashboard.Dashboard;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    ArrayList<HomeObject> mlist = new ArrayList<>();
    LinearLayout dashboard_button;

    HomeAdapter homeadapter;

    public Home() {
        // Required empty public constructor
    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainActivity.Homeshown = true;
        Log.e("BackPressed","True");
        if (view == null) {
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_home, container, false);

            dashboard_button = view.findViewById(R.id.dashboard_button);
            dashboard_button.setOnClickListener(this);

            recyclerView = (RecyclerView) view.findViewById(R.id.home_recyclerview);
            gridLayoutManager = new GridLayoutManager(getContext(),2, LinearLayoutManager.VERTICAL,false);
            //ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(0);
            recyclerView.setLayoutManager(gridLayoutManager);
            //recyclerView.addItemDecoration(itemOffsetDecoration);

            //setting arraylist
            setList();

            homeadapter = new HomeAdapter(mlist,getContext());
            recyclerView.setAdapter(homeadapter);
        }
        return view;
    }

    private void setList() {
        CreatemlistElements("CoronaMelange",R.mipmap.icon_cm);
        CreatemlistElements("Dangal",R.mipmap.icon_dangal);
        CreatemlistElements("SAS Council",R.mipmap.icon_sascouncil);
        CreatemlistElements("News",R.mipmap.icon_news);
        CreatemlistElements("Results",R.mipmap.icon_results);
        CreatemlistElements("Event Finder",R.mipmap.icon_eventfinder);
        CreatemlistElements("Nitp Clubs",R.mipmap.nitpclubs);
    }
    private void CreatemlistElements(String name,int imageResId){
        HomeObject homeObject = new HomeObject();
        homeObject.setName(name);
        homeObject.setImageResId(imageResId);
        mlist.add(homeObject);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dashboard_button:
                startActivity(new Intent(getContext(),Dashboard.class));
        }
    }
}
