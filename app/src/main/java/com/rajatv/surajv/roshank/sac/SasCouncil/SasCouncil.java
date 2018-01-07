package com.rajatv.surajv.roshank.sac.SasCouncil;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rajatv.surajv.roshank.sac.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SasCouncil extends Fragment {

    RecyclerView recyclerView;
    ArrayList<String> mlist = new ArrayList<>();


    public SasCouncil() {
        // Required empty public constructor
    }


    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            // Inflate the layout for this fragment
            view=inflater.inflate(R.layout.fragment_sas_council, container, false);

            recyclerView = view.findViewById(R.id.events_recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setHasFixedSize(true);
            //recyclerView.setAdapter(new SacCouncilAdapter());
        }
        return view;
    }

}
