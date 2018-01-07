package com.rajatv.surajv.roshank.sac.Results;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rajatv.surajv.roshank.sac.MainActivity;
import com.rajatv.surajv.roshank.sac.R;

/**
 * Created by CREATOR on 11/14/2017.
 */

public class Dangal_results extends Fragment{


    public Dangal_results() {
        // Required empty public constructor
    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainActivity.Homeshown = false;
        if (view == null) {
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_updates, container, false);
        }
        return view;
    }
}
