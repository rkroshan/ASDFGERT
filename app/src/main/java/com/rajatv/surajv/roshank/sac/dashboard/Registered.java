package com.rajatv.surajv.roshank.sac.dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.Signup.SignUp1;
import com.rajatv.surajv.roshank.sac.StringVariable;

import java.util.ArrayList;
import java.util.Map;

import static android.os.Build.VERSION_CODES.M;
import static java.security.AccessController.getContext;

/**
 * Created by CREATOR on 11/10/2017.
 */

public class Registered extends Fragment {

    private LinearLayout registered_linearlayout,register_popup_linearlayout;
    private Button register_button;
    private SharedPreferences pref,pref1;
    Map<String,?> eventsData;
    ArrayList<RegisterObject>  mlist = new ArrayList<>();
    private RecyclerView recyclerView;
    private RegisterAdapter registerAdapter;

    public Registered() {
        // Required empty public constructor
    }


    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            // Inflate the layout for this fragment
            view=inflater.inflate(R.layout.registered_events_background, container, false);
            pref = getActivity().getSharedPreferences(StringVariable.SHAREDPREFERENCE_STUDENTEVENTDATA,0);
            pref1 = getActivity().getSharedPreferences(StringVariable.SHAREDPREFERENCE_STUDENTDATA,0);
            init(view);
        }
        return view;
    }

    private void init(View v) {
        registered_linearlayout = v.findViewById(R.id.registered_linearlayout);
        register_popup_linearlayout = v.findViewById(R.id.register_popup_linearlayout);
        register_button = v.findViewById(R.id.register_button);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SignUp1.class));
            }
        });

        if(pref1.getBoolean(StringVariable.REGISTERED,false)){
            registered_linearlayout.setVisibility(View.VISIBLE);
            Log.i("Registered","registered_linearlayout");
            register_popup_linearlayout.setVisibility(View.GONE);
            initRecyclerview();
        }else {
            registered_linearlayout.setVisibility(View.GONE);
            register_popup_linearlayout.setVisibility(View.VISIBLE);
            Log.i("Registered","register_popup_linearlayout");

        }
    }

    private void initRecyclerview() {
        setdata();
        recyclerView = view.findViewById(R.id.dashboard_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        registerAdapter = new RegisterAdapter(mlist,getActivity());
        recyclerView.setAdapter(registerAdapter);
    }

    private void setdata() {
        Log.e("Registered : ","setData---");
        eventsData = pref.getAll();
        for(Map.Entry<String,?> event:eventsData.entrySet()){
            Log.e("Registered : ",event.getKey());
            if(event.getValue()!="0"){
                String key = event.getKey();
                mlist.add(new RegisterObject(""+key.charAt(0),key));
            }
        }
    }
}
